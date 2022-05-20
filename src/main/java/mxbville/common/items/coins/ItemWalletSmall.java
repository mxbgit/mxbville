package mxbville.common.items.coins;

import mxbville.MxBville;
import mxbville.common.items.ItemBase;

public class ItemWalletSmall extends ItemBase {

	public ItemWalletSmall(String name) {
		super(name);
		setMaxStackSize(1);
		this.setCreativeTab(MxBville.ITEMTAB);
	}

}
