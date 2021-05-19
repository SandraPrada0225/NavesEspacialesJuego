package IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import gameObjects.Constantes;

public class JsonParser {

	public static ArrayList<ScoreDate> readfile() throws FileNotFoundException{
		ArrayList<ScoreDate> dataList= new ArrayList<ScoreDate>();
		
		File file = new File(Constantes.SCOREPATH);
		if(!file.exists() || file.length()==0) {
			System.out.print("la lista está vacia");
			return dataList;
		}
		JSONTokener parser= new JSONTokener(new FileInputStream(file));
		JSONArray jsonList= new JSONArray(parser);
		for (int i = 0; i < jsonList.length(); i++) {
			JSONObject obj= (JSONObject) jsonList.get(i);
			ScoreDate data= new ScoreDate();
			data.setScore(obj.getInt("score"));
			data.setDate(obj.getString("date"));
			System.out.print("la lista no está vacia");
			dataList.add(data);
			
		}
		return dataList;
	}
	
	
	public static void writeFile(ArrayList<ScoreDate> dataList) throws IOException {
		
		File outFile = new File(Constantes.SCOREPATH);
		outFile.getParentFile().mkdirs();
		outFile.createNewFile();
		JSONArray jsonList= new JSONArray();
		for (ScoreDate data : dataList) {
			JSONObject obj= new JSONObject();
			obj.put("score",data.getScore());
			obj.put("date",data.getDate());
			
			jsonList.put(obj);
			
		}
		
		BufferedWriter bufferedWriter= Files.newBufferedWriter(Paths.get(outFile.toURI()));
		jsonList.write(bufferedWriter);
		bufferedWriter.close();
	}
}
