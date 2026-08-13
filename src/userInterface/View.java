package userInterface;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import operation.BusinessLogic;
import pojo.Priority;
import pojo.Task;
// import jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectMapper;


public class View {
static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
final Runnable task=()->
		{
			autosave();
			System.out.println("File saved successfully!");
		};

final Runnable notification=()->
{
	List<Task> tasks=BusinessLogic.tasks;
	tasks.stream().filter(t->t.getDueDate().isEqual(LocalDate.now())).toList().forEach(t->System.out.println(t));
};

public void getNotification()
{
	System.out.println("**************NOTIFICATION(Near due tasks!)***********");
	executor.schedule(notification, 5, TimeUnit.SECONDS);
}
	public void start()
	{
		executor.scheduleAtFixedRate(task, 1, 5, TimeUnit.SECONDS);
	}

	public void end()
	{
		try{
			executor.awaitTermination(5, TimeUnit.MILLISECONDS);
		}
		catch(InterruptedException ex)
		{
			if(Thread.interrupted())
			{
				System.out.println("failed to save the file");
			}
		}
		System.out.println("Autosave task ended!");
	}


	public static void autosave()
	{
		File file = new File("src\\file.json");
		try{
			if(file.createNewFile())
			{
				System.out.println("File was not existed before...Now it's created!");
			}

			List<Task> tasks= BusinessLogic.tasks;
		List<String> habits=BusinessLogic.habits;

		ObjectMapper mapper = new ObjectMapper();
		
		Map<String,Object> map = new HashMap<>();

		
			map.put("tasks",tasks);
			map.put("habits",habits);

						
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, map);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		
		
	}

	public static void main(String[] args)
	{	
		View obj = new View();
		obj.start();
		
		Scanner sc = new Scanner(System.in);
		while(true)
		{
			System.out.println("Hey! What you want see today!");
			
			System.out.println("1. View-Section");
			System.out.println("2. Create-Section");
			System.out.println("3. History");
			
			System.out.println("Enter a Number: ");
			int num = sc.nextInt();
			
			switch(num)
			{
			case 1: System.out.println("a. Today's Tasks?");
					System.out.println("b. Overdue Tasks?");
					System.out.println("c. Weekly Stats?");
					break;
			case 2: 
					System.out.println("d Update Tasks?");
					System.out.println("e Create Tasks?");
					System.out.println("f Delete Tasks?");
					break;
			case 3: System.out.println("g Viewing History: ");
					System.out.println("h Filter Tasks:");
				  	break;
				  	
			default : System.out.println("Invalid choice!");
					 
			}
			
			
			
			System.out.println("Enter a Option number: ");
			char secondSelection = sc.next().charAt(0);
									sc.nextLine();
			
			switch(secondSelection)
			{
			case 'a': System.out.println("View Today's Tasks: ");
					   System.out.println(BusinessLogic.getTodayTasks());
					   break;
			case 'b': System.out.println("View Overdue Tasks: ");
						System.out.println(BusinessLogic.viewDueTasks());
					   break;
						
			case 'c': System.out.println("View Weekly Tasks: ");
					   BusinessLogic.weeklyStats();
					   break;
					   
			case 'd': System.out.println("Update Tasks?");
					   System.out.println("Enter taskID: ");
					   Long id = sc.nextLong();
					   Task task=BusinessLogic.getTaskById(id);
					   BusinessLogic.updateTask(task);
					   break;
					   
					   
			case 'e': System.out.println("Creating Tasks...");
					   System.out.println("Enter title of task: ");
					   String title=sc.nextLine();
					   
					   System.out.println("Write description about task: (10-12 words)");
					   String desc = sc.nextLine();
					   
					   System.out.println("Assign priority of the task: High/Medium/Low");
					   String prior=sc.nextLine();
					   
					   Priority p= Priority.valueOf(prior.toUpperCase());
					   
					   
						   
					   System.out.println("Enter Deadline (yyyy-MM-dd):");
					   String d = sc.nextLine();
					   
					   DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					   
					   LocalDate date=LocalDate.parse(d,format);
					   
					   Task newTask = new Task(title,desc,p,date,LocalDate.now(),pojo.Status.PENDING);
					   BusinessLogic.addTask(newTask);
					   
					   break;
					   
					   
			case 'f': System.out.println("Delete tasks!");
					   System.out.println("Enter id for the task!");
					   id=sc.nextLong();
					   sc.nextLine();
					   BusinessLogic.deleteTask(id);
					   break;
					   
			case 'g': System.out.println("Viewing history!");
					   System.out.println( BusinessLogic.viewHistory());
					   break;
			
					   
			case 'h': System.out.println("Enter task's Status");
					   sc.nextLine();
					   String status = sc.nextLine();
					   System.out.println("Enter task's Date");
					   String dateInput=sc.nextLine();
					   System.out.println("Enter task's Priority");
					   String priority = sc.nextLine();
					   
					  List<Task> tasks= BusinessLogic.findTask(dateInput, status, priority);
					  
					  tasks.forEach(t->System.out.println(t));
					
					  break;
				
			default: System.out.println("Invalid Choice!");
			
			}
			obj.getNotification();
			obj.end();
		}
		
	}

}
