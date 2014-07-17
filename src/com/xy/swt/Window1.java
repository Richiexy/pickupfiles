package com.xy.swt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.xy.tools.PickupFilesByDate;

public class Window1 {
	
	private Display display;
	
	private Shell shell;

	public Window1(){
		display = new Display();//创建一个display对象。
		shell = new Shell(display);//shell是程序的主窗体
        shell.setText("java小程序！");
        shell.setSize(400, 300);
        
        Menu mainMenu = new Menu(shell,SWT.BAR);
        shell.setMenuBar(mainMenu);
        
        MenuItem fileItem = new MenuItem(mainMenu,SWT.CASCADE);
        fileItem.setText("文件(&F)");
        
        Menu fileMenu = new Menu(shell,SWT.DROP_DOWN);
        fileItem.setMenu(fileMenu);
        MenuItem newFileItem = new MenuItem(fileMenu,SWT.PUSH);
        newFileItem.setText("项 目 \tCtrl+Shift+N");
        newFileItem.setAccelerator(SWT.CTRL+SWT.SHIFT+'N');//定义快捷键Ctrl+Shift+N
        
        MenuItem separator1 =  new MenuItem(fileMenu, SWT.SEPARATOR);
        
        MenuItem pakageItem =  new MenuItem(fileMenu, SWT.PUSH);
        pakageItem.setText("包(&p)");
        
        MenuItem menuItem2 = new MenuItem(mainMenu, SWT.CASCADE);
        menuItem2.setText("帮助(&H)");
        
        CLabel srcFilelLbel = new CLabel(shell,SWT.NONE);
        srcFilelLbel.setText("filepath");
        srcFilelLbel.setBounds(10,10,70,20);
        final Text srcFileText = new Text(shell, SWT.NONE);
        srcFileText.setBounds(90, 10, 100, 20);//设置坐标和长宽
        Button srcFileButton = new Button(shell,SWT.NONE);
        srcFileButton.setBounds(200, 10, 50, 20);
        srcFileButton.setText("浏览...");
        srcFileButton.addSelectionListener(new SelectionAdapter(){
        	 public void widgetSelected(SelectionEvent e) {
        		 System.out.print("-filepath:");
        		 srcFileText.setText(folderDig(shell));
        	 }
        });
        
        CLabel toDirlLbel = new CLabel(shell,SWT.NONE);
        toDirlLbel.setText("todir");
        toDirlLbel.setBounds(10,40,70,20);
        final Text toDirText = new Text(shell, SWT.NONE);
        toDirText.setBounds(90, 40, 100, 20);//设置坐标和长宽
        Button toDirButton = new Button(shell,SWT.NONE);
        toDirButton.setBounds(200, 40, 50, 20);
        toDirButton.setText("浏览...");
        toDirButton.addSelectionListener(new SelectionAdapter(){
       	 public void widgetSelected(SelectionEvent e) {
       		 System.out.print("-todir:");
       		 toDirText.setText(folderDig(shell));
       	 }
        });
        
        CLabel dateLbel = new CLabel(shell,SWT.NONE);
        dateLbel.setText("date");
        dateLbel.setBounds(10,70,70,20);
        final Text dateText= new Text(shell, SWT.NONE);
        dateText.setBounds(90, 70, 100, 20);//设置坐标和长宽
        Date curDate =new Date();
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateText.setText(sdf.format(curDate));
		
        final MessageBox msg = new MessageBox(shell, SWT.OK|SWT.CANCEL);
        final PickupFilesByDate pickupfiles = new PickupFilesByDate();
        
        Button submitButton = new Button(shell,SWT.NONE);
        submitButton.setBounds(50, 100, 50, 20);
        submitButton.setText("开始");
        submitButton.addSelectionListener(new SelectionAdapter(){
          	 public void widgetSelected(SelectionEvent e) {
          		String filepath = srcFileText.getText();
          		String todir = toDirText.getText();
          		String date = dateText.getText();
          		
          		if( "".equals(filepath) || filepath==null )
          			msg.setMessage("请输入！");
          		else {
          			pickupfiles.setS_filePath(filepath.replaceAll("\\\\","/"));
          			pickupfiles.setS_index(filepath.replaceAll("\\\\","/").lastIndexOf("/"));
          		}
          		
          		if( "".equals(todir) || todir==null )
          			msg.setMessage("请输入！");
          		else {
	          		pickupfiles.setS_toDir(todir.replaceAll("\\\\","/"));
          		}
          		
          		if( "".equals(date) || date==null )
          			msg.setMessage("请输入！");
          		else {
	          		pickupfiles.setS_date(date);
          		}
          		
          		try {
					pickupfiles.run();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
          	 }
           });
        
        shell.open();      //打开主窗体
        while(!shell.isDisposed()){  //如果主窗体没有关闭则一直循环
            if(!display.readAndDispatch()){  //如果display不忙
            display.sleep();    //休眠
            }
        }
        display.dispose();      //销毁display
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}
	/**
    * 文件夹（目录）选择对话框
    */
	public String folderDig(Shell parent){
        //新建文件夹（目录）对话框
        DirectoryDialog folderdlg=new DirectoryDialog(parent);
        //设置文件对话框的标题
        folderdlg.setText("文件选择");
        //设置初始路径
        folderdlg.setFilterPath("SystemDrive");
        //设置对话框提示文本信息
        folderdlg.setMessage("请选择相应的文件夹");
        //打开文件对话框，返回选中文件夹目录
        String selecteddir=folderdlg.open();
        if(selecteddir==null){
            return "";
        }
        else{
            System.out.println("您选中的文件夹目录为："+selecteddir);  
            return selecteddir;
        }                    
    }
	
	/**
    * 文件选择对话框
    */
    public String fileDig(Shell parent){
        //新建文件对话框，并设置为打开的方式
        FileDialog filedlg=new FileDialog(parent,SWT.OPEN);
        //设置文件对话框的标题
        filedlg.setText("文件选择");
        //设置初始路径
        filedlg.setFilterPath("SystemRoot");
        //打开文件对话框，返回选中文件的绝对路径
        String selected=filedlg.open();
        System.out.println("您选中的文件路径为："+selected);   
        return selected;
    }
}
