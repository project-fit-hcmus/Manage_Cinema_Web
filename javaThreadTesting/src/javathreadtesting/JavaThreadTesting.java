/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javathreadtesting;

/**
 *
 * @author User
 */
public class JavaThreadTesting extends Thread{
    private Thread t;
    private String threadName;
    
    public JavaThreadTesting(String name){
        this.threadName = name;
        System.out.println("Created " + this.threadName);
    }
    
    @Override
    public void run(){
        System.out.println("Running " + this.threadName);
        try{
            for(int i = 10; i > 0; --i){
                System.out.println("Thread " + threadName + " --- " + i );
                Thread.sleep(60);
            }
        }catch(InterruptedException e){
            System.out.println("Thread " + threadName + " interrupted!!");
        }
        System.out.println("Thread " + threadName + " Existing!!!");
    }
    
    public void start(){
        System.out.println("Starting " + threadName);
        if(t == null){
            t = new Thread(this,threadName);
            t.start();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JavaThreadTesting t1 = new JavaThreadTesting("Demo 1");
        t1.start();
        JavaThreadTesting t2 = new JavaThreadTesting("Demo 2");
        t2.start();
    }
    
}
