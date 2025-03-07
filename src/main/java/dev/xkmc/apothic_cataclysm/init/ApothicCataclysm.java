package dev.xkmc.apothic_cataclysm.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(ApothicCataclysm.MODID)
@Mod.EventBusSubscriber(modid = ApothicCataclysm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ApothicCataclysm {

	public static final String MODID = "apothic_cataclysm";
	public static final Registrate REGISTRATE = Registrate.create(MODID);

	public ApothicCataclysm() {
		ACModConfig.init();
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {

	}

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, ACTagGen::genEntityTag);
	}

}
