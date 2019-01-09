package space.bbkr.gluon.TitleScroll;

import me.elucent.earlgray.api.Trait;
import net.minecraft.nbt.CompoundTag;

public class StringTrait extends Trait {
	String value = "";

	public StringTrait() {
	}

	@Override
	public CompoundTag write(CompoundTag tag) {
		tag.putString("value", value);
		return tag;
	}

	@Override
	public Trait read(CompoundTag tag) {
		this.value = tag.getString("value");
		return this;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
