package dev.xkmc.apothic_cataclysm.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ACModConfig {

	public static class Common {

		public final ForgeConfigSpec.DoubleValue chanceToReplaceBossWeapon;
		public final ForgeConfigSpec.DoubleValue chanceToReplaceNormalWeapon;
		public final ForgeConfigSpec.DoubleValue dropChanceOverride;

		public final ForgeConfigSpec.BooleanValue enableAncientSpear;
		public final ForgeConfigSpec.BooleanValue enableCursedBow;
		public final ForgeConfigSpec.BooleanValue enableWrathOfTheDesert;
		public final ForgeConfigSpec.BooleanValue enableWitherAssultShoulderWeapon;
		public final ForgeConfigSpec.BooleanValue enableVoidAssultShoulderWeapon;
		public final ForgeConfigSpec.BooleanValue enableLaserGatling;
		public final ForgeConfigSpec.BooleanValue enableMeatShredder;
		public final ForgeConfigSpec.BooleanValue enableSoulRender;
		public final ForgeConfigSpec.BooleanValue enableGauntletOfMaelstrom;
		public final ForgeConfigSpec.BooleanValue enableInfernalForge;
		public final ForgeConfigSpec.BooleanValue enableVoidForge;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("spawn");
			chanceToReplaceBossWeapon = builder.defineInRange("chanceToReplaceBossWeapon", 0.05d, 0, 1);
			chanceToReplaceNormalWeapon = builder.defineInRange("chanceToReplaceNormalWeapon", 0.5d, 0, 1);
			dropChanceOverride = builder.defineInRange("dropChanceOverride", 0.1d, 0, 1);
			builder.pop();
			builder.push("weapon_toggles");
			enableAncientSpear = builder.define("enableAncientSpear", true);
			enableCursedBow = builder.define("enableCursedBow", true);
			enableWrathOfTheDesert = builder.define("enableWrathOfTheDesert", true);
			enableWitherAssultShoulderWeapon = builder.define("enableWitherAssultShoulderWeapon", true);
			enableVoidAssultShoulderWeapon = builder.define("enableVoidAssultShoulderWeapon", true);
			enableLaserGatling = builder.define("enableLaserGatling", false);
			enableMeatShredder = builder.define("enableMeatShredder", true);
			enableSoulRender = builder.define("enableSoulRender", true);
			enableGauntletOfMaelstrom = builder.define("enableGauntletOfMaelstrom", false);
			enableInfernalForge = builder.define("enableInfernalForge", true);
			enableVoidForge = builder.define("enableVoidForge", true);
			builder.pop();
		}


	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {

		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	public static void init() {
		register(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	private static void register(ModConfig.Type type, IConfigSpec<?> spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = mod.getModId() + "-" + type.extension() + ".toml";
		ModLoadingContext.get().registerConfig(type, spec, path);
	}

}
