
import java.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;



public class Save {
    
    public int save(JScrollPane js,String path,String name)
    {
        
        JTextArea jt=(JTextArea)js.getViewport().getView();
        String str=jt.getText();
        BufferedWriter bw=null;
        FileWriter fw=null;
        try{
            fw=new FileWriter(path+"/"+name);
            bw=new BufferedWriter(fw);
            bw.write(str);
            bw.close();
            fw.close();
            System.out.println("done");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 1;
    }
    
}
