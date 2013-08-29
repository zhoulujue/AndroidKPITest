

package sogou.test.kpi.communication;

import java.util.HashMap;
import java.util.Map;

/**
 * ֵ
 *  
 * @author hg1086892
 * @version	0.1	2011/05/20
 */

public class KeyCode {
	
	private Map<String, String> map = new HashMap<String, String>();
	private static KeyCode _instance=null;
	
	public String getKeyCode(String key){
		String keyCode = map.get(key);
		return keyCode;
	}
	
	public static KeyCode getInstance(){
		if(_instance==null){
			_instance=new KeyCode();
		}
		return _instance;
	}
	
	public KeyCode(){		
		map.put("soft_left", "1");
		map.put("soft_right", "2");
		map.put("home", "3");
		map.put("back", "4");
		map.put("call", "5");
		map.put("endcall", "6");
		map.put("0", "7");
		map.put("1", "8");
		map.put("2", "9");
		map.put("3", "10");
		map.put("4", "11");
		map.put("5", "12");
		map.put("6", "13");
		map.put("7", "14");
		map.put("8", "15");
		map.put("9", "16");
		map.put("*", "17");
		map.put("#", "18");
		map.put("up", "19");
		map.put("down", "20");
		map.put("left", "21");
		map.put("right", "22");
		map.put("center", "23");
		map.put("volume_up", "24");
		map.put("volume_down", "25");
		map.put("power", "26");
		map.put("camara", "27");
		map.put("clear", "28");
		map.put("a", "29");
		map.put("b", "30");
		map.put("c", "31");
		map.put("d", "32");
		map.put("e", "33");
		map.put("f", "34");
		map.put("g", "35");
		map.put("h", "36");
		map.put("i", "37");
		map.put("j", "38");
		map.put("k", "39");
		map.put("l", "40");
		map.put("m", "41");
		map.put("n", "42");
		map.put("o", "43");
		map.put("p", "44");
		map.put("q", "45");
		map.put("r", "46");
		map.put("s", "47");
		map.put("t", "48");
		map.put("u", "49");
		map.put("v", "50");
		map.put("w", "51");
		map.put("x", "52");
		map.put("y", "53");
		map.put("z", "54");
		map.put("COMMA", "55");
		map.put(".", "56");
		map.put("ALT_LEFT", "57");
		map.put("ALT_RIGHT", "58");
		map.put("SHIFT_LEFT", "59");
		map.put("SHIFT_RIGHT", "60");
		map.put("TAB", "61");
		map.put("space", "62");
		map.put("SYM", "63");
		map.put("EXPLORER", "64");
		map.put("ENVELOPE", "65");
		map.put("enter", "66");
		map.put("del", "67");
		map.put("GRAVE", "68");
		map.put("MINUS", "69");
		map.put("EQUALS", "70");
		map.put("LEFT_BRACKET", "71");
		map.put("RIGHT_BRACKET", "72");
		map.put("BACKSLASH", "73");//"\"
		map.put(";", "74");
		map.put("'", "75");
		map.put("SLASH", "76");
		map.put("@", "77");
		map.put("NUM", "78");
		map.put("HEADSETHOOK", "79");
		map.put("FOCUS", "80"); // *Camera*focus
		map.put("plus", "81");
		map.put("menu", "82");
		map.put("NOTIFICATION", "83");
		map.put("search", "84");
		map.put("MEDIA_PLAY_PAUSE", "85");
		map.put("MEDIA_STOP", "86");
		map.put("MEDIA_NEXT", "87");
		map.put("MEDIA_PREVIOUS", "88");
		map.put("MEDIA_REWIND", "89");
		map.put("MEDIA_FAST_FORWARD", "90");
		map.put("mute", "91");
	}
	
}

