package mxbville.common.items.documents;

import mxbville.common.config.MxBvilleConfig;

public class ItemBaitInvitation extends ItemInvitation{

	public ItemBaitInvitation(String name) {
		super(name);
	}
	
	@Override
	public double getSuccessRate() {
		return MxBvilleConfig.baitSuccess;
	}

}
