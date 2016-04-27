package fetchKrx;

import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.eugenefe.batchjob.KrxParsingJob;

public class $9100SchedulerTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($9100SchedulerTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($9100SchedulerTest.class);


	public static void main(String[] args) throws Exception {
//		cronTriggerTest();
		simpleTriggerTest();
		
	}
	private static void cronTriggerTest() throws Exception{
		//Quartz 1.6.3
    	//JobDetail job = new JobDetail();
    	//job.setName("dummyJobName");
    	//job.setJobClass(HelloJob.class);    	
    	JobDetail job = JobBuilder.newJob(KrxParsingJob.class).withIdentity("dummyJobName", "group1").build();

    	//Quartz 1.6.3
    	//CronTrigger trigger = new CronTrigger();
    	//trigger.setName("dummyTriggerName");
    	//trigger.setCronExpression("0/5 * * * * ?");
    	
    	Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
    							.withSchedule( CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
    							.build();
    	
    	//schedule it
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	scheduler.start();
    	scheduler.scheduleJob(job, trigger);
	}
	
	private static void simpleTriggerTest() throws Exception {
		// Quartz 1.6.3
				// JobDetail job = new JobDetail();
				// job.setName("dummyJobName");
				// job.setJobClass(HelloJob.class);

		JobDetail job = JobBuilder.newJob(KrxParsingJob.class).withIdentity("dummyJobName", "group1").build();

		// Quartz 1.6.3
		// SimpleTrigger trigger = new SimpleTrigger();
		// trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		// trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		// trigger.setRepeatInterval(30000);

		// Trigger the job to run on the next round minute
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
						.withSchedule(SimpleScheduleBuilder.simpleSchedule()
														   .withIntervalInSeconds(5)
														   .repeatForever())
						.build();

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);

	}
}
