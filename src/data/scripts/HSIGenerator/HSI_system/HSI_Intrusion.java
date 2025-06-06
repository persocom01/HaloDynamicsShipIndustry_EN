package data.scripts.HSIGenerator.HSI_system;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.ids.Terrain;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import data.scripts.HSIGenerator.HSIDreadnought;

public class HSI_Intrusion implements SectorGeneratorPlugin {
        @Override
        public void generate(SectorAPI sector) {
                StarSystemAPI system = sector.createStarSystem("Intrusion");
                system.getLocation().set(580f, -17370f);
                system.setLightColor(new Color(155, 155, 200));// light color in entire system, affects all entities
                LocationAPI hyper = Global.getSector().getHyperspace();
                system.setBackgroundTextureFilename("graphics/backgrounds/hyperspace1.jpg");

                PlanetAPI HSI_HomeStar = system.initStar("HSI_Intrusion", "HSI_Intrusion", 600f, 0f);



                // add planet 1
                PlanetAPI HSI_Wanderer = system.addPlanet("HSI_Wanderer", // Unique id for this planet (or null to
                                                                          // have it be autogenerated)
                                HSI_HomeStar, // What the planet orbits (orbit is always circular)
                                "Wanderer", // Name
                                "HSI_Wanderer", // Planet type id in planets.json
                                0, // Starting angle in orbit, i.e. 0 = to the right of the star
                                220, // Planet radius, pixels at default zoom
                                7000, // Orbit radius, pixels at default zoom
                                550);// Days it takes to complete an orbit. 1 day = 10 seconds.
                List<MarketConditionAPI> initCons = HSI_Wanderer.getMarket().getConditions();
                for (MarketConditionAPI Con : initCons) {
                        HSI_Wanderer.getMarket().removeCondition(Con.getId());
                }
                HSI_Wanderer.setInteractionImage("illustrations","HSI_Wanderer");

                PlanetAPI HSI_Minion = system.addPlanet("HSI_Minion", // Unique id for this planet (or null to
                                                                      // have it be autogenerated)
                                HSI_Wanderer, // What the planet orbits (orbit is always circular)
                                "Minion", // Name
                                "HSI_Minion", // Planet type id in planets.json
                                0, // Starting angle in orbit, i.e. 0 = to the right of the star
                                100, // Planet radius, pixels at default zoom
                                800, // Orbit radius, pixels at default zoom
                                55);// Days it takes to complete an orbit. 1 day = 10 seconds.
                initCons = HSI_Minion.getMarket().getConditions();
                for (MarketConditionAPI Con : initCons) {
                        HSI_Minion.getMarket().removeCondition(Con.getId());
                }
                HSI_Minion.setInteractionImage("illustrations","HSI_Minion");

                // 创造地图实体
                SectorEntityToken HSI_SpaceBridge = system.addCustomEntity("HSI_SpaceBridge", // id
                                null, // name
                                "HSI_SpaceBridge", // type id in planets.json
                                "HSI");// faction id
                // 设置实体的运动轨道
                HSI_SpaceBridge.setCircularOrbitPointingDown(HSI_HomeStar, 0, 1500, 30);// which to orbit, starting
                                                                                        // angle, radius,
                // orbit days
                // 创造市场，绑在上面创建的实体上
                MarketAPI HSI_SpaceBridge_Market = Global.getFactory().createMarket("HSI_SpaceBridge_Market",
                                HSI_SpaceBridge.getName(),
                                4);// id, name, size
                // HSI_SpaceBridge_Market.setPlanetConditionMarketOnly(true);
                HSI_SpaceBridge_Market.setPrimaryEntity(HSI_SpaceBridge);
                HSI_SpaceBridge_Market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
                HSI_SpaceBridge_Market.setFactionId("HSI");
                HSI_SpaceBridge_Market.addCondition("population_4");
                HSI_SpaceBridge_Market.addIndustry("population");
                HSI_SpaceBridge_Market.addIndustry("spaceport");
                HSI_SpaceBridge_Market.addIndustry("HSI_GF3_HQ");
                HSI_SpaceBridge_Market.addIndustry("HPSID");
                HSI_SpaceBridge_Market.addSubmarket("open_market");
                HSI_SpaceBridge_Market.addSubmarket("storage");
                HSI_SpaceBridge_Market.getTariff().modifyFlat("default_tariff",
                                HSI_SpaceBridge_Market.getFaction().getTariffFraction());
                HSI_SpaceBridge.setMarket(HSI_SpaceBridge_Market);
                HSI_SpaceBridge_Market.getMemoryWithoutUpdate().set("$noBar", true);
                HSI_SpaceBridge.addTag(Tags.STORY_CRITICAL);
                HSI_SpaceBridge_Market.addTag(Tags.STORY_CRITICAL);
                // 把市场加入经济系统
                Global.getSector().getEconomy().addMarket(HSI_SpaceBridge_Market, false);// marketAPI, isJunkAround

                // 创造地图实体
                SectorEntityToken HSI_Wanderer_Station = system.addCustomEntity("HSI_Wanderer_Station", // id
                                null, // name
                                "HSI_Wanderer_Station", // type id in planets.json
                                "HSI");// faction id
                // 设置实体的运动轨道
                HSI_Wanderer_Station.setCircularOrbitPointingDown(HSI_Wanderer, 0, 500, 40);// which to orbit, starting
                                                                                            // angle,
                // radius,
                // orbit days
                // 创造市场，绑在上面创建的实体上
                MarketAPI HSI_Wanderer_Station_Market = Global.getFactory().createMarket("HSI_Wanderer_Station_Market",
                                HSI_Wanderer.getName(),
                                7);// id, name, size
                HSI_Wanderer.setMarket(HSI_Wanderer_Station_Market);
                HSI_Wanderer_Station_Market.setPrimaryEntity(HSI_Wanderer);
                HSI_Wanderer_Station_Market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
                HSI_Wanderer_Station_Market.setFactionId("HSI");
                HSI_Wanderer_Station_Market.addCondition("population_6");
                HSI_Wanderer_Station_Market.addIndustry("population");
                HSI_Wanderer_Station_Market.addIndustry("megaport");
                HSI_Wanderer_Station_Market.addIndustry("heavybatteries");
                HSI_Wanderer_Station_Market.addIndustry("highcommand");
                HSI_Wanderer_Station_Market.addIndustry("starfortress_high");
                HSI_Wanderer_Station_Market.addIndustry("fuelprod");
                HSI_Wanderer_Station_Market.addIndustry("HPSID");
                HSI_Wanderer_Station_Market.addIndustry("orbitalworks", Arrays.asList("pristine_nanoforge"));
                HSI_Wanderer_Station_Market.addIndustry("HSI_Home_Relay");
                HSI_Wanderer_Station_Market.addSubmarket("generic_military");
                HSI_Wanderer_Station_Market.addSubmarket("open_market");
                HSI_Wanderer_Station_Market.addSubmarket("black_market");
                HSI_Wanderer_Station_Market.addSubmarket("storage");
                HSI_Wanderer_Station_Market.addCondition("cold");
                HSI_Wanderer_Station_Market.addCondition("poor_light");
                HSI_Wanderer_Station_Market.addCondition("HSI_HeatingWorld");
                HSI_Wanderer_Station_Market.getTariff().modifyFlat("default_tariff",
                                HSI_SpaceBridge_Market.getFaction().getTariffFraction());
                HSI_Wanderer_Station_Market.getConnectedEntities().add(HSI_Wanderer_Station);
                HSI_Wanderer.setFaction("HSI");
                HSI_Wanderer_Station.setMarket(HSI_Wanderer_Station_Market);
                // 把市场加入经济系统
                Global.getSector().getEconomy().addMarket(HSI_Wanderer_Station_Market, false);// marketAPI, isJunkAround

                MarketAPI HSI_Minion_Market = Global.getFactory().createMarket("HSI_Minion_Market",
                                HSI_Wanderer.getName(),
                                4);// id, name, size
                HSI_Minion.setMarket(HSI_Minion_Market);
                HSI_Minion_Market.setPrimaryEntity(HSI_Minion);
                HSI_Minion_Market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
                //HSI_Minion_Market.setFactionId("HSI_Stalker");
                HSI_Minion_Market.setFactionId("HSI");
                HSI_Minion_Market.addCondition("population_4");
                HSI_Minion_Market.addIndustry("population");
                HSI_Minion_Market.addIndustry("spaceport");
                HSI_Minion_Market.addIndustry("grounddefenses");
                HSI_Minion_Market.addIndustry("patrolhq");
                HSI_Minion_Market.addIndustry("orbitalstation_high");
                HSI_Minion_Market.addIndustry("commerce");
                HSI_Minion_Market.addIndustry("lightindustry");
                HSI_Minion_Market.addSubmarket("open_market");
                HSI_Minion_Market.addSubmarket("black_market");
                HSI_Minion_Market.addSubmarket("storage");
                HSI_Minion_Market.addCondition("cold");
                HSI_Minion_Market.addCondition("poor_light");
                HSI_Minion_Market.addCondition("HSI_HeatingWorld");
                HSI_Minion_Market.getTariff().modifyFlat("default_tariff",
                                HSI_SpaceBridge_Market.getFaction().getTariffFraction());
                //HSI_Minion.setFaction("HSI_Stalker");
                HSI_Minion.setFaction("HSI");
                // HSI_Minion_Market.getConnectedEntities().add(HSI_Minion);

                // 把市场加入经济系统
                Global.getSector().getEconomy().addMarket(HSI_Minion_Market, true);// marketAPI, isJunkAround

                SectorEntityToken HSI_Relay = system.addCustomEntity("HSI_Home_Relay", // id
                                "HSI EHOF Relay", // name
                                "HSI_Home_Relay", // type id in planets.json
                                "HSI");// faction id
                HSI_Relay.setCircularOrbitPointingDown(HSI_Wanderer, 0, 3000, 90);// which to orbit, starting angle,
                                                                                  // radius, orbit
                // days

                /*
                 * SectorEntityToken focus, java.lang.String category, java.lang.String key,
                 * float bandWidthInTexture, int bandIndex, java.awt.Color color,
                 * float bandWidthInEngine, float middleRadius, float orbitDays,
                 * java.lang.String terrainId, java.lang.String optionalName
                 */
                system.addRingBand(HSI_Wanderer, "misc", "rings_ice0", 256f, 0, Color.white, 256f, 2000, 80f);
                system.addRingBand(HSI_Wanderer, "misc", "rings_ice0", 256f, 0, Color.white, 256f, 2000, 140f);
                system.addRingBand(HSI_Wanderer, "misc", "rings_ice0", 256f, 1, Color.white, 256f, 2000, 160f,
                                Terrain.RING,
                                "Cloud Ring");

                system.addRingBand(HSI_HomeStar, "misc", "rings_ice0", 256f, 0, Color.white, 256f, 1500, 100f);
                system.addRingBand(HSI_HomeStar, "misc", "rings_ice0", 256f, 0, Color.white, 256f, 1500, 160f);
                system.addRingBand(HSI_HomeStar, "misc", "rings_ice0", 256f, 1, Color.white, 256f, 1500, 180f,
                                Terrain.RING,
                                "Cloud Ring");

                // generates hyperspace destinations for in-system jump points
                system.autogenerateHyperspaceJumpPoints(true, true);

                HSIDreadnought.addFleet(HSI_SpaceBridge);

                cleanup(system);
        }

        // from Tart scripts
        // Clean nearby Nebula(nearby system)
        private void cleanup(StarSystemAPI system) {
                HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
                NebulaEditor editor = new NebulaEditor(plugin);
                float minRadius = plugin.getTileSize() * 2f;

                float radius = system.getMaxRadiusInHyperspace();
                editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius * 0.5f, 0, 360f);
                editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
        }

}
