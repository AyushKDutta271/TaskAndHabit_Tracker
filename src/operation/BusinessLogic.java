package operation;
import pojo.Status;
import pojo.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;



public class BusinessLogic {

	public static List<Task> tasks=new ArrayList<>();
	public static List<String> habits=new ArrayList<>();

	static AtomicInteger atomic = new AtomicInteger();
	public static void addTask(Task task)
	{
		task.setId((long)atomic.addAndGet(1));
		tasks.add(task);
		System.out.println("Task successfully created!");
	}
	
	public static void deleteTask(Long id)
	{
		Task task=getTaskById(id);
		tasks.remove(task);
		
		System.out.println("Task is removed Succesfully!");
		
	}
	
	public static List<Task> getTodayTasks()
	{
		return tasks.stream().filter(t->t.getCreatedDate().isEqual(LocalDate.now())).toList();
	}
	
	public static List<Task> viewAllPreviousTasks()
	{
		return tasks.stream().filter(t->t.getStatus().equals(Status.COMPLETED)).toList();
	}
	public static List<Task> viewDueTasks()
	{
		return tasks.stream().filter(x->LocalDate.now().isAfter(x.getDueDate())).toList();
	}
	
	public static void weeklyStats()
	{
		List<Task> weeklyTasks=	tasks.stream().filter(t->t.getDueDate()!=null)
							.filter(t->t.getDueDate().isAfter(LocalDate.now().minusDays(7)))
							.filter(t->t.getDueDate().isBefore(LocalDate.now()))
							.toList();
		long total=weeklyTasks.size();
		long completed= weeklyTasks.stream().filter(t->t.getStatus().equals(Status.COMPLETED)).count();
		System.out.println("Out of "+total+completed +" in this week!");


	}

	public static void averageTasksPerDay()
	{
		long completed= tasks.stream().filter(t->t.getDueDate().equals(LocalDate.now())&&t.getStatus().equals(Status.COMPLETED)).count();
		long totalTasks=tasks.stream().filter(t->t.getDueDate().equals(LocalDate.now())).count();

		System.out.println("Average tasks completed today: "+(completed/totalTasks) + " and percentage: "+ ((completed*100)/totalTasks));

	}

	public static void calculateStreaks()
	{
		int streak=0;
		List<Task> completedTasks=tasks.stream().sorted((t1,t2)->t1.getDueDate().compareTo(t2.getDueDate())).toList();
		for(int i=0; i<completedTasks.size(); i++)
			{
				if(completedTasks.get(i).getStatus().equals(Status.COMPLETED))
					streak++;
				else
					break;
			}		  

			if(streak>=21)
			{
				tasks.stream().forEach(t->habits.add(t.getTitle()));
			}

			System.out.println("Current streak is:"+streak +" for the tasks: "+habits);
	} 
	
	public static List<Task> viewHistory()
	{
		return tasks;
	}
	
	public static List<Task> findTask(String date,String status, String priority)
	{
		DateTimeFormatter d = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		
		LocalDate searchdate=	LocalDate.parse(date,d);
		
		return tasks.stream().filter(t->t.getDueDate().isEqual(searchdate) && t.getStatus().toString().toLowerCase().equals(status.toLowerCase()) && t.getPriority().toString().toLowerCase().equals(priority.toLowerCase())).toList();
	}
	
	public static void updateTask(Task task)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("What you want to update? ");
		

		System.out.println(
			"a. Status"+
			"\nb. Due Date"+
			"\nc. title"+
			"\nd. description"
		);
		char action=sc.next().charAt(0);
		switch(action)
		{
		case 'a': System.out.println("Updating status: ");
					  System.out.println("SELECT 'Completed', 'Pending', 'Canceled' ");
					  String status=sc.nextLine().toUpperCase();
					  switch(status)
					  {
					  case "CCOMPLETED": task.setStatus(Status.COMPLETED);break;
					  case "PENDING": task.setStatus(Status.PENDING); break;
					  case "CANCELED": task.setStatus(Status.CANCELED); break;
					  default : System.out.println("Invalid status!");
					  }
					  System.out.println("Task's status updated!");
					  break;
					  
		case 'b':System.out.println("Adjusting due date!");
					  System.out.println("Enter the date(yyyy-mm-dd)");
					  String date=sc.nextLine();
					  DateTimeFormatter  format=DateTimeFormatter.ofPattern( "yyyy-mm-dd");
					  LocalDate d= LocalDate.parse(date, format);

					  task.setDueDate(d);
					  System.out.println("Date updated successfully!");
					  break;
		case 'c':System.out.println("Updating title...");
				String title=sc.nextLine();
				task.setTitle(title);
				System.out.println("Title updated succesfully!");
				break;
		
		case 'd':System.out.println("Updating description...");
				String desc=sc.nextLine();
				task.setDescription(desc);
				System.out.println("Description updated successfully!");
				break;
		
		default: System.out.println("IInvalid choice!");
		
		}
	}
	
	public static Task getTaskById(Long id)
	{
		return tasks.stream().filter(t->t.getId().equals(id)).findFirst().orElseThrow(()->new NoSuchElementException("No such task is found!"));
	}
}
