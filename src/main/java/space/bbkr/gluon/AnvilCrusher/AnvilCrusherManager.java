package space.bbkr.gluon.AnvilCrusher;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sun.istack.internal.Nullable;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

public class AnvilCrusherManager implements ResourceReloadListener {
	public static final int PREFIX_LENGTH = "anvil_crush/".length();
	public static final int SUFFIX_LENGTH = ".json".length();
	private static final Map<Identifier, AnvilCrush> crushMap = Maps.newHashMap();
	private boolean hadErrors;

	public AnvilCrusherManager() {

	}

	@Override
	public void onResourceReload(ResourceManager manager) {
		Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
		this.hadErrors = false;
		this.crushMap.clear();
		Iterator itr = manager.findResources("crusher_setups", (var0) -> var0.endsWith(".json")).iterator();

		while(itr.hasNext()) {
			Identifier id = (Identifier)itr.next();
			String path = id.getPath();
			Identifier subId = new Identifier(id.getNamespace(), path.substring(PREFIX_LENGTH, path.length() - SUFFIX_LENGTH));

			try {
				Resource res = manager.getResource(id);
				Throwable error = null;

				try {
					JsonObject json = JsonHelper.deserialize(gson, IOUtils.toString(res.getInputStream(), StandardCharsets.UTF_8), JsonObject.class);
					if (json == null) {
						//LOGGER.error("Couldn't load recipe {} as it's null or empty", var6);
					} else {
						this.add(AnvilCrushSerializer.read(subId, json));
					}
				} catch (Throwable t) {
					error = t;
					throw t;
				} finally {
					if (res != null) {
						if (error != null) {
							try {
								res.close();
							} catch (Throwable var18) {
								error.addSuppressed(var18);
							}
						} else {
							res.close();
						}
					}

				}
			} catch (IllegalArgumentException | JsonParseException exc) {
				AnvilCrushLogger.error("Parsing error loading setup {}", subId, exc);
				this.hadErrors = true;
			} catch (IOException exc) {
				AnvilCrushLogger.error("Couldn't read custom advancement {} from {}", subId, id, exc);
				this.hadErrors = true;
			}
		}

		AnvilCrushLogger.info("Loaded {} setups", this.crushMap.size());
	}

	public void add(AnvilCrush crush) {
		if (this.crushMap.containsKey(crush.getId())) {
			throw new IllegalStateException("Duplicate setup ignored with ID " + crush.getId());
		} else {
			this.crushMap.put(crush.getId(), crush);
		}
	}

	@Nullable
	public static AnvilCrush get(World world, BlockPos pos) {
		Iterator itr = crushMap.values().iterator();

		AnvilCrush setup;
		do {
			if (!itr.hasNext()) {
				return null;
			}

			setup = (AnvilCrush) itr.next();
		} while(!setup.matches(world.getBlockState(pos).getBlock()));

		return setup;
	}

	@Nullable
	public static AnvilCrush get(Identifier var1) {
		return (AnvilCrush) crushMap.get(var1);
	}
}
