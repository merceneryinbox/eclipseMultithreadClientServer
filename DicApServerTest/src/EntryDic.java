
import java.io.Serializable;

/**
 * Created by mercenery on 01.06.2017.
 */
public class EntryDic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SEARCH = 0;
	public static final int ADD    = 1;
	public static final int DELETE = - 1;
	public static final int REDUCT = 10;
	public static final int ERROR = 2;
	private static String word;
	private static String definition;
	int flag;
	
	/**
	 * Constructor which takes three variables <code> flag </code>, <code> word </code> and <code> definition </code>.
	 *
	 * @param flag
	 * @param word
	 * @param definition
	 */
	public EntryDic(int flag, String word, String definition){
		this.flag = flag;
		EntryDic.word = word;
		EntryDic.definition = definition;
	}
	
	public static int getSearchFlag(){
		return SEARCH;
	}
	
	public static int getAddFlag(){
		return ADD;
	}
	
	public static int getDeleteFlag(){
		return DELETE;
	}
	
	public static int getCorrectFlag(){
		return REDUCT;
	}
	
	public static String getWord(){
		return word;
	}
	
	public String getDefinition(){
		return definition;
	}
	
	public int getFlag(){
		return flag;
	}
}
