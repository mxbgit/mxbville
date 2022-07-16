package mxbville.common.calc.generators;

import mxbville.common.calc.math.MxRand;
import mxbville.common.items.documents.ItemReplyLetter;
import mxbville.util.MxRef;
import net.minecraft.item.ItemStack;

public class ReplyMailGenerator {
	
	public static ItemStack generate()
	{
		boolean male = MxRand.get().nextBoolean();
		String name = male?PersonalityGenerator.getRandomMaleName():PersonalityGenerator.getRandomFemaleName();
		int mailType = (male?ItemReplyLetter.MailType_NewVillagerMale : ItemReplyLetter.MailType_NewVillagerFemale);
		int traitID = PersonalityGenerator.getRandomTrait();
		return ItemReplyLetter.generateMail(name, MxRef.MOD_ID + ":reply.newvillager" + MxRand.get().nextInt(5) + ".content", traitID ,mailType);

	}
	
}
