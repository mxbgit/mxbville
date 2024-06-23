package mxbville.common.config.jsonData;


public class JsonTradingRecipe {
	public String[] inputs;
	public String output;	
	
	/**
	 * The String format for the input and output items is:
	 * [domainName,iremID,amount,meta].
	 * Example: [minecraft,log,1,0]
	 * You can have up to 4 output item stacks.
	 * The amount should be between 1 and 64
	 * 
	 * @param inputs
	 * @param output
	 */
	public JsonTradingRecipe(String[] inputs, String output)
	{
		this.inputs = inputs;
		this.output = output;
	}
}
