

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.DefaultFacebookClient;
import com.restfb.json.JsonObject;
import com.restfb.json.JsonArray;
import com.restfb.types.*;
import com.restfb.Parameter;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

class Example 
{
  
    private static String MY_ACCESS_TOKEN=null;
  
    public static void main(String args[])throws IOException
    {
    	MY_ACCESS_TOKEN = "CAACEdEose0cBAOF8KZBzM6CiGIR5ZAeC4g7ytcPqkCMVVKt4YDZCp9IUZBc2yYoME2UcvgCuEjZCFq4KDnnlt7BBZBROU7YTJVFAD8UBiI2Ya2PFfVyRPZA8diPrZCPjvAeb5szEmietrw5O6uDpenqA4UZCxiUuzCyMaD0R4NQ1qHSAcO21appH9ktHSPD3SpIvtgsznMagR4AYV7JZALpZC5e";
    	FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);

		User user = facebookClient.fetchObject("me", User.class);
		JsonObject j = facebookClient.fetchObject("me/groups",JsonObject.class);
		JsonArray ja = j.getJsonArray("data");
		int size = ja.length();
		String comp = "COMPs...";
		String id = null;
		for(int i=0;i<size;i++)
		{
			String name = ja.getJsonObject(i).getString("name");
			//System.out.println(name);
			if(comp.equals(name))
			{
				id= ja.getJsonObject(i).getString("id");
				break;
			}
		}
		
		j = facebookClient.fetchObject(id+"/feed",JsonObject.class);
		ja = j.getJsonArray("data");
		
		//Writing group feed to a file
		FileWriter fw = new FileWriter(new File("feed.txt"));
		BufferedWriter bw = new BufferedWriter(fw);
		size = ja.length();
		String line = "------------------------------------------------------------------------------------------------------------";
		for(int i=0;i<size;i++)
		{
			
			String post_id = ja.getJsonObject(i).getString("id");
			JsonObject temp = facebookClient.fetchObject(post_id,JsonObject.class);
			
			Post cur_post = facebookClient.fetchObject(post_id,Post.class,Parameter.with("fields", "message,from"));
			temp = temp.getJsonObject("from");
			String admin_creator = temp.getString("name");
			
			bw.write(cur_post.getMessage()+"\n");	
			bw.write(line+"\n");
			bw.write("CREATOR: " + admin_creator+"\n");
			bw.write(line+"\n");
		}	
		bw.close();
		fw.close();

/*
		Connection<User> myfriends = facebookClient.fetchConnection("me/groups", User.class);

		System.out.println("Count of my friends: " + myfriends.getData().size());

		System.out.println("User name: "+ user.getName());
  */
    }


}