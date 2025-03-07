package dev.xkmc.apothic_cataclysm.init;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ACTagGen {

	public static final TagKey<EntityType<?>> VALID_MELEE_TARGET = tag("valid_melee_bosses");
	public static final TagKey<EntityType<?>> VALID_RANGED_TARGET = tag("valid_raged_bosses");

	public static void genEntityTag(RegistrateTagsProvider.IntrinsicImpl<EntityType<?>> pvd) {
		pvd.addTag(VALID_MELEE_TARGET)
				.add(EntityType.ZOMBIE, EntityType.DROWNED, EntityType.HUSK, EntityType.VINDICATOR,
						EntityType.ZOMBIFIED_PIGLIN, EntityType.WITHER_SKELETON
				);
		pvd.addTag(VALID_RANGED_TARGET).add(
				EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON
		);
	}

	private static TagKey<EntityType<?>> tag(String id) {
		return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(ApothicCataclysm.MODID, id));
	}

}
