import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


public class main {

	// Lugar donde guardo los datos
	public static HashMap<String, Integer> values = new HashMap<String, Integer>();
	
	// Variable tipo flag para los datos que voy a parear
	/*
	 * --> 0 = word count
	 * --> 1 = time
	 * --> 2 = emoji
	 */
	public static int type = 0;
	public static void main(String[] args) {
	
		dataParser();
		System.out.println(values);
		
	}
	
	private static void dataParser() {
		
		// Primero parseamos los datos
		try {
			  File data = new File("datos_personales.txt");
		      Scanner scanner = new Scanner(data);
		      while (scanner.hasNextLine()) {
		        String aux = scanner.nextLine();
		        System.out.println(aux);
		        parse(aux);
		      }
		      scanner.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		
		// Ahora creamos el archivo csv
		try {
		  File csv = null;
		  switch (type) {
		  	case 0:
		  		csv = new File("csv_wordcount_data.csv");
		  		break;
		  	case 1:
		  		csv = new File("daily_data.csv");
		  		break;
		  	case 2:
		  		csv = new File("emoji_data.csv");
		  		break;
		  }
	      if (csv.createNewFile()) {
	    	  System.out.println("File created: " + csv.getName());
	    	  String fileName = "";
	    	  switch(type) {
	    	  	case 0:
			  		fileName = "csv_wordcount_data.csv";
			  		break;
			  	case 1:
			  		fileName = "daily_data.csv";
			  		break;
			  	case 2:
			  		fileName = "emoji_data.csv";
			  		break;
	    	  }
	    	  
	    	  FileWriter myWriter = new FileWriter(fileName);
	    	  switch (type) {
			  	case 0:
			  		myWriter.write("Words, Words Counted\n");
			  		break;
			  	case 1:
			  		myWriter.write("Hour, Messages Counted\n");
			  		break;
			  	case 2:
			  		myWriter.write("Emoji, Emojis Counted\n");
			  		break;
			  }
	    	  values.forEach((k,v)->{
	    		  try {
	    			  myWriter.write(k + ',' + String.valueOf(v) + '\n');
	    		  } catch (IOException e) {
	    			  // TODO Auto-generated catch block
	    			  e.printStackTrace();
	    		  }
	    	  });
	    	  myWriter.close();
	      } else {
	    	  System.out.println("File already exists.");
	      }
		} catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
		}
		
	}
	
	private static void parse(String aux) {
		String value = "", key = "";
		int flag = 0;
		for(int i = 0 ; i < aux.length() ; i++ ) {
			if(aux.charAt(i) == ' ') {
				// Lo ignoramos
			} else {
				if(aux.charAt(i)==':')
					flag++;
				else {
					if(flag == 0)
						value += aux.charAt(i);
					else
						key += aux.charAt(i);
				}
			}
		}
		if(values.containsKey(key)) {
			int res = values.get(key);
			switch (type) {
		  	case 0: // lo calcula como una suma
		  	case 2:
				values.replace(key, res + Integer.valueOf(value));
				break;
		  	case 1: // lo calcula como un promedio
		  		values.replace(key, (res + Integer.valueOf(value))/2);
				break;
			}
		} else {
			System.out.println(key);
			System.out.println(value);
			values.put(key, Integer.valueOf(value));
		}
	}

}
