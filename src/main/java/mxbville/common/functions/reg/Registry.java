package mxbville.common.functions.reg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registry <T extends IRegistrable> {
	
	private HashMap<String,T> mapIntData = new HashMap<String,T>();
	private List<T> dataList = new ArrayList<T>();
	
	public List<T> getAll(){
		return dataList;
	}
	
	public void register(String regID, T data ){
		if(mapIntData.containsKey(regID)){
			System.out.println("Can not register the object, type/class is existed!");
		}
		else{
			mapIntData.put(regID, data);
			dataList.add(data);
			data.setRegID(regID);
		}
	}
	
	public T get(String regID){
		if(mapIntData.containsKey(regID)){
			return mapIntData.get(regID);
		}
		else{
			System.out.println("Can not fint registed item where id =" + regID);
			return null;
		}
	}
}
