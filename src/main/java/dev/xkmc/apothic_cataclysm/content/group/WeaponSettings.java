package dev.xkmc.apothic_cataclysm.content.group;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public record WeaponSettings(ForgeConfigSpec.BooleanValue config, Item item, List<ResourceLocation> advancements) {

	public boolean isValid(ServerPlayer player) {
		if (!config.get())
			return false;
		var plAdv = player.getAdvancements();
		var manager = player.server.getAdvancements();
		for (var e : advancements) {
			var adv = manager.getAdvancement(e);
			if (adv == null) return false;
			if (!plAdv.getOrStartProgress(adv).isDone()) return false;
		}
		return true;
	}
}
