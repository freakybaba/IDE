
import java.io.*;

public class open {
    
    BufferedReader br=null;
    FileReader fr=null;
    
    String get(String p)
    {
        String s="";   
        try{
            
            fr=new FileReader(p);
            br=new BufferedReader(fr);
            
            String curr;
            while((curr=br.readLine())!=null)
            {
                s=s+curr;
                s=s+"\n";
            }
            
            if(br!=null)
                br.close();
            if(fr!=null)
                fr.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return s;
    }
}
