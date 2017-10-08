package Sample3;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.lang.System.out;


public final class ChatUsers extends JFrame implements ActionListener {
    String username;
    PrintWriter pw;
    BufferedReader br;
    JTextArea chatmsg;
    JTextField chatip;
    JButton send, exit;
    Socket chatusers;
    
    public ChatUsers(String uname,String servername)throws Exception
    {
        super(uname);
        this.username = uname;
        chatusers = new Socket(servername,80);
        br = new BufferedReader(new InputStreamReader(chatusers.getInputStream()));
        pw = new PrintWriter(chatusers.getOutputStream(),true);
        pw.println(uname);
        buildInterface();
        new MessageThread().start();
    }
    
    public void buildInterface()
    {
        send = new JButton("Send");
        exit = new JButton("Exit");
        chatmsg = new JTextArea();
        chatmsg.setRows(30);
        chatmsg.setColumns(50);
        chatip = new JTextField(50);
        JScrollPane sp = new JScrollPane(chatmsg,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp,"Center");
        JPanel bp = new JPanel(new FlowLayout());
        bp.add(chatip);
        bp.add(send);
        bp.add(exit);
        bp.setBackground(Color.LIGHT_GRAY);
        bp.setName("CHAT APP");
        add(bp,"North");
        send.addActionListener(this);
        exit.addActionListener(this);
        setSize(500,300);
        setVisible(true);
        pack();
    }
    
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource()==exit)
        {
            pw.println("end");
            System.exit(0);
        }
        
        else
        {
            pw.println(chatip.getText());
            chatip.setText(null);
        }
    }

    public static void main(String ... args)
    {
        String SetUserName = JOptionPane.showInputDialog(null,"Please enter your "
                + "name: ","CHAT APP",JOptionPane.PLAIN_MESSAGE);
        String servername = "localhost";
        try
        {
            new ChatUsers(SetUserName,servername);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    class MessageThread extends Thread
    {
        public void run()
        {
            String line;
            
            try
            {
                while(true)
                {
                    line = br.readLine();
                    chatmsg.append(line+"\n");
                }
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
        }
    }
}

