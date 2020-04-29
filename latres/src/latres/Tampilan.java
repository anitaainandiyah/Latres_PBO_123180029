package latres;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.table.DefaultTableModel;

public class Tampilan {
    public static void main(String[] args){
    ktk x = new ktk();
}   
}
class ktk extends JFrame{
    String [][] data = new String [500][3];
    public String sql = "";
    String DBurl= "jdbc:mysql://localhost/latrespbo";
    String DBusername = "root";
    String DBpassword = "";
    Connection cn;
    Statement st;
    ResultSet rs;

    
    JLabel atas = new JLabel ("Kontak Nomor");
    JLabel nama = new JLabel ("Nama");
    final JTextField fnama = new JTextField(30);
    JLabel nomor = new JLabel ("Nomor");
    final JTextField fnomor = new JTextField(30);
    
    JButton hapus = new JButton("hapus");
    JButton simpan = new JButton("simpan");
    JButton edit = new JButton("edit");
    JButton tambah = new JButton ("tambah");
    
    DefaultTableModel model;
    JTable tabel;
    JScrollPane scrollpane;
    
    public ktk(){
        
        String[] judul = {"nama","nomor"};
        model = new DefaultTableModel (judul,0);
        tabel = new JTable (model);
        tabel.setModel(model);
        tampil();
        scrollpane = new JScrollPane (tabel);
        setVisible(true);
     
    this.getContentPane().setBackground(Color.gray);
    setTitle ("input data");
    setDefaultCloseOperation(3);
    setSize(600,600);
    this.getContentPane().setBackground(Color.red);
        
    setLayout(null);
    add(atas);
    add(nama);
    add(nomor);
    add(fnama);
    add(fnomor);
    add(hapus);
    add(edit);
    add(simpan);
    add(tambah);
    add(scrollpane);
    
    atas.setBounds(20,10,300,50);
    nama.setBounds(20,100,100,20);
    fnama.setBounds(100,100,140,20);
    nomor.setBounds(20,130,100,20);
    fnomor.setBounds(100,130,140,20);
    hapus.setBounds(90,230,80,20);
    simpan.setBounds(190,230,80,20);
    edit.setBounds(290,230,80,20);
    tambah.setBounds(390,230,80,20);
    scrollpane.setBounds (10,270,550,100);
    
    setVisible(true);
    setAlwaysOnTop(false);
    setResizable(false);
    
    simpan.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent bb){
            inputData();
            tampil();
        }
    });
    
    tambah.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent bb){
        reset();
        tampil();
        }
    });
    
     hapus.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent bb){
        hapus();
        tampil();
        }
    });
    
    tabel.addMouseListener(new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent me){
        klik(me);
        }
    });
    
     edit.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent bb){
        edit();
        tampil();
        }
    });
    }
    
   public void klik(MouseEvent me){
        int i = tabel.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tabel.getModel();
        fnama.setText(model.getValueAt(i,0).toString());
        fnomor.setText(model.getValueAt(i,1).toString());
    }
    
    private void tampil(){
        try{
            int row = tabel.getRowCount();
            for(int a=0; a<row; a++){
                model.removeRow(0);
            }
         Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/latrespbo","root","");
         Statement st = cn.createStatement();
         ResultSet rs = st.executeQuery("Select * from kontak"); 
         int max=0;
         while(rs.next()){
             String data[] = {rs.getString(1),rs.getString(2)};
             model.addRow(data);
             max++;
         }
        }catch (SQLException ex){
            Logger.getLogger(Tampilan.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    private void inputData(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/latrespbo","root","");
            Statement st = cn.createStatement();
            st.executeUpdate("INSERT INTO kontak VALUES ("+fnama.getText()+","+fnomor.getText()+")");
            DefaultTableModel model = (DefaultTableModel) tabel.getModel();
            model.setRowCount(0);
            tampil();
            JOptionPane.showMessageDialog(null, "data berhasil disimpan!", "Hasil", JOptionPane.INFORMATION_MESSAGE);
            st.close();
            cn.close();
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "data gagal disimpan!", "Hasil", JOptionPane.ERROR_MESSAGE);
            }catch (ClassNotFoundException ex){
                JOptionPane.showMessageDialog(null, "driver tidak ditemukan!", "Hasil", JOptionPane.ERROR_MESSAGE);
         } 
        }
    
    private void reset(){
        fnama.setText("");
        fnomor.setText("");   
    }
    
    private void hapus (){
        try{
           Class.forName("com.mysql.jdbc.Driver");
           cn = DriverManager.getConnection(DBurl,DBusername,DBpassword); 
           sql = "DELETE FROM kontak where nama ='"+fnama.getText() + "'";
           st = cn.createStatement();
           st.execute(sql);
           javax.swing.JOptionPane.showMessageDialog(null, "data di hapus");
           ResultSet rs = st.executeQuery("select * from kontak");
           while(rs.next()){
               String data[] = {rs.getString(1), rs.getString(2)};
               model.addRow(data);
           }
           } catch(Exception e){
               javax.swing.JOptionPane.showMessageDialog(null, "gagal proses hapus data");
           }
    }
    
     private void edit (){
        try{
           Class.forName("com.mysql.jdbc.Driver");
           cn = DriverManager.getConnection(DBurl,DBusername,DBpassword); 
           sql = "UPDATE kontak SET nomor ='"+fnomor.getText() + "' WHERE nama = '"+fnama.getText()+"'";
           st = cn.createStatement();
           st.execute(sql);
           javax.swing.JOptionPane.showMessageDialog(null, "data berhasil di edit");
           ResultSet rs = st.executeQuery("select * from kontak");
           while(rs.next()){
               String data[] = {rs.getString(1), rs.getString(2)};
               model.addRow(data);
           }
           } catch(Exception e){
               javax.swing.JOptionPane.showMessageDialog(null, "gagal proses hapus data");
           }
    }
    
    
    
    
    
}