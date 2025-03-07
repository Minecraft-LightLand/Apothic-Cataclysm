package dev.xkmc.apothic_cataclysm.content.group;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModItems;
import dev.xkmc.apothic_cataclysm.init.ACModConfig;
import dev.xkmc.apothic_cataclysm.init.ACTagGen;
import dev.xkmc.mob_weapon_api.example.vanilla.VanillaMobManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;

import java.util.ArrayList;
import java.util.List;

public class GroupPreset {

	public static final List<WeaponSettings> RANGED, MELEE;

	static {
		RANGED = List.of(
				new WeaponSettings(ACModConfig.COMMON.enableCursedBow, ModItems.CURSED_BOW.get(), List.of(adv("kill_maledictus"))),
				new WeaponSettings(ACModConfig.COMMON.enableWrathOfTheDesert, ModItems.WRATH_OF_THE_DESERT.get(), List.of(adv("kill_remnant"), adv("kill_maledictus"))),
				new WeaponSettings(ACModConfig.COMMON.enableWitherAssultShoulderWeapon, ModItems.WITHER_ASSULT_SHOULDER_WEAPON.get(), List.of(adv("kill_harbinger"))),
				new WeaponSettings(ACModConfig.COMMON.enableVoidAssultShoulderWeapon, ModItems.VOID_ASSULT_SHOULDER_WEAPON.get(), List.of(adv("kill_harbinger"), adv("kill_ender_golem"))),
				new WeaponSettings(ACModConfig.COMMON.enableLaserGatling, ModItems.LASER_GATLING.get(), List.of(adv("kill_harbinger")))
		);
		MELEE = List.of(
				new WeaponSettings(ACModConfig.COMMON.enableAncientSpear, ModItems.ANCIENT_SPEAR.get(), List.of(adv("find_cursed_pyramid"))),
				new WeaponSettings(ACModConfig.COMMON.enableMeatShredder, ModItems.MEAT_SHREDDER.get(), List.of(adv("kill_harbinger"))),
				new WeaponSettings(ACModConfig.COMMON.enableSoulRender, ModItems.SOUL_RENDER.get(), List.of(adv("kill_maledictus"))),
				new WeaponSettings(ACModConfig.COMMON.enableGauntletOfMaelstrom, ModItems.GAUNTLET_OF_MAELSTROM.get(), List.of(adv("kill_ender_guardian"), adv("kill_remnant"))),
				new WeaponSettings(ACModConfig.COMMON.enableInfernalForge, ModItems.INFERNAL_FORGE.get(), List.of(adv("kill_monstrosity"))),
				new WeaponSettings(ACModConfig.COMMON.enableVoidForge, ModItems.VOID_FORGE.get(), List.of(adv("kill_monstrosity"), adv("kill_ender_golem")))
		);
	}

	private static ResourceLocation adv(String id) {
		return new ResourceLocation(Cataclysm.MODID, id);
	}

	public static void apply(ServerPlayer sp, PathfinderMob e) {
		var list = new ArrayList<WeaponSettings>();
		if (e.getType().is(ACTagGen.VALID_MELEE_TARGET)) list.addAll(MELEE);
		if (e.getType().is(ACTagGen.VALID_RANGED_TARGET)) list.addAll(RANGED);
		if (list.isEmpty()) return;
		var valid = new ArrayList<Item>();
		for (var entry : list) {
			if (entry.isValid(sp)) {
				valid.add(entry.item());
			}
		}
		if (valid.isEmpty()) return;
		Item item = valid.get(e.getRandom().nextInt(valid.size()));
		ItemStack stack = item.getDefaultInstance();
		if (VanillaMobManager.attachGoal(e, stack)) {
			e.setItemInHand(InteractionHand.MAIN_HAND, stack);
			e.setDropChance(EquipmentSlot.MAINHAND, (float) (double) ACModConfig.COMMON.dropChanceOverride.get());
		}
	}

}
