package dev.xkmc.apothic_cataclysm.event;

import com.github.L_Ender.cataclysm.Cataclysm;
import dev.xkmc.apothic_cataclysm.content.group.GroupPreset;
import dev.xkmc.apothic_cataclysm.init.ACModConfig;
import dev.xkmc.apothic_cataclysm.init.ApothicCataclysm;
import dev.xkmc.mob_weapon_api.example.vanilla.VanillaMobManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ApothicCataclysm.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobSpawnEventHandler {

	@SubscribeEvent
	public static void onMobJoinLevel(EntityJoinLevelEvent event) {
		var e = event.getEntity();
		if (!(e instanceof PathfinderMob mob)) return;
		if (!mob.getPersistentData().getBoolean("apoth.boss")) return;
		if (e.getTags().contains(ApothicCataclysm.MODID + "_checked")) {
			if (e.getTags().contains(ApothicCataclysm.MODID + "_applied")) {
				ItemStack stack = mob.getMainHandItem();
				VanillaMobManager.attachGoal(mob, stack);
			}
			return;
		}
		e.addTag(ApothicCataclysm.MODID + "_checked");
		ItemStack old = mob.getMainHandItem();
		var chance = old.isEmpty() ? 1 : isApothWeapon(old) ?
				ACModConfig.COMMON.chanceToReplaceBossWeapon.get() :
				ACModConfig.COMMON.chanceToReplaceNormalWeapon.get();
		if (mob.getRandom().nextDouble() > chance) return;
		var root = e.getRootVehicle();
		var player = root.level().getNearestPlayer(root, 128);
		if (!(player instanceof ServerPlayer sp) || player instanceof FakePlayer) return;
		GroupPreset.apply(sp, mob);
	}

	@SubscribeEvent
	public static void onAttack(LivingAttackEvent event) {
		var attacker = event.getSource().getEntity();
		if (!(attacker instanceof PathfinderMob mob)) return;
		if (!mob.getPersistentData().getBoolean("apoth.boss")) return;
		if (!mob.getMainHandItem().getItemHolder().unwrapKey().orElseThrow().location().getNamespace().equals(Cataclysm.MODID))
			return;
		var target = event.getEntity();
		if (mob.getTarget() != target) {
			event.setCanceled(true);
		}
	}

	private static boolean isApothWeapon(ItemStack stack) {
		var tag = stack.getTag();
		return tag != null && tag.getBoolean("apoth_boss");
	}

}
