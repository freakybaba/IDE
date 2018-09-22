

import java.io.File;


public class Script {

  
    public void scr(String n,String s) 
    {
               //d is working directory this is s+"/Resource"
               //n is name of script
               //s is path of file
               String d=s+"/Resource";
               String r=null;
               if(n.equals("ZEH"))
                   r="./a.out";
               else
               {
                   r="java "+n;
               }
               ProcessBuilder pb1=new ProcessBuilder("/bin/sh","-c","echo  ' #!/bin/sh \n gnome-terminal\\'> "+n+".sh");
               ProcessBuilder pb2=new ProcessBuilder("/bin/sh","-c","echo ' --tab\\'>> "+n+".sh");
               ProcessBuilder pb2a=new ProcessBuilder("/bin/sh","-c","echo ' --working-directory=\""+s+"\"\\'>> "+n+".sh");
               ProcessBuilder pb2b=new ProcessBuilder("/bin/sh","-c","echo -n ' -e \"bash -c '>> "+n+".sh");
               ProcessBuilder pb3=new ProcessBuilder("/bin/sh","-c","echo -n \"\'"+r+"\';\' \" >> "+n+".sh");
               ProcessBuilder pb4=new ProcessBuilder("/bin/sh","-c","echo -n 'printf \\' >> "+n+".sh");
               ProcessBuilder pb5=new ProcessBuilder("/bin/sh","-c","echo -n '\"\\' >> "+n+".sh");
               ProcessBuilder pb6=new ProcessBuilder("/bin/sh","-c","echo -n 'npress enter to exit :\\' >> "+n+".sh");
               ProcessBuilder pb7=new ProcessBuilder("/bin/sh","-c","echo -n '\"' >> "+n+".sh"); 
               ProcessBuilder pb8=new ProcessBuilder("/bin/sh","-c","echo -n \" \';\' read a \' \" >> "+n+".sh");
               ProcessBuilder pb9=new ProcessBuilder("/bin/sh","-c","echo -n ' \"\\ ' >> "+n+".sh");
               ProcessBuilder pby=new ProcessBuilder("/bin/sh","-c","chmod +x "+n+".sh");
               pb1.directory(new File(d)); 
               pb2.directory(new File(d));
               pb2a.directory(new File(d));
               pb2b.directory(new File(d));
               pb3.directory(new File(d));
               pb4.directory(new File(d));
               pb5.directory(new File(d));
               pb6.directory(new File(d));
               pb7.directory(new File(d));
               pb8.directory(new File(d));
               pb9.directory(new File(d));
               pby.directory(new File(d));
               try{
                   Process p1=pb1.start();
                   while(p1.isAlive());
                   Process p2=pb2.start();
                   while(p2.isAlive());
                   Process p2a=pb2a.start();
                   while(p2a.isAlive());
                   Process p2b=pb2b.start();
                   while(p2b.isAlive());
                   Process p3=pb3.start();
                   while(p3.isAlive());
                   Process p4=pb4.start();
                   while(p4.isAlive());
                   Process p5=pb5.start();
                   while(p5.isAlive());
                   Process p6=pb6.start();
                   while(p6.isAlive());
                   Process p7=pb7.start();
                   while(p7.isAlive());
                   Process p8=pb8.start();
                    while(p8.isAlive());
                   Process p9=pb9.start();
                   while(p9.isAlive());
                   Process py=pby.start();
               }
               catch(Exception e)
               {
                   System.out.println(e);
               }
    }
    
}
