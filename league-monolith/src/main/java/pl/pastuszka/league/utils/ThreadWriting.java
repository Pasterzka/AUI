package pl.pastuszka.league.utils;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import pl.pastuszka.league.entity.League;

public class ThreadWriting {
    
    public static void parallelThread(List<League> leagues) {
        ForkJoinPool customThreadPool = new ForkJoinPool(4);

		final List<League> finalLeagues = leagues; 

		try {
			customThreadPool.submit(() ->{
				finalLeagues.parallelStream().forEach(league -> {
    				System.out.println("\n---" + Thread.currentThread().getName() + " Liga: " + league.getName() + " ---");

    				league.getTeams().forEach(team -> {
        				System.out.println("--- " + Thread.currentThread().getName() + " Druzyna: " + team.toString() + " ---");
    				});

    				System.out.println("--- " + Thread.currentThread().getName() + " Koniec ligi: " + league.getName() + " ---\n");
				});
			}).get();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Zamknięcie puli wątków
			customThreadPool.shutdown();
			try {
				if (!customThreadPool.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
					customThreadPool.shutdownNow();
				}
			} catch (InterruptedException e) {
				customThreadPool.shutdownNow();
			}
		}
    }

    public static void streamThread(List<League> leagues) {
        ForkJoinPool customThreadPool = new ForkJoinPool(4);

		final List<League> finalLeagues = leagues; 

		try {
			customThreadPool.submit(() ->{
				finalLeagues.stream().forEach(league -> {
    				System.out.println("\n---" + Thread.currentThread().getName() + " Liga: " + league.getName() + " ---");

    				league.getTeams().forEach(team -> {
        				System.out.println("--- " + Thread.currentThread().getName() + " Druzyna: " + team.toString() + " ---");
    				});

    				System.out.println("--- " + Thread.currentThread().getName() + " Koniec ligi: " + league.getName() + " ---\n");
				});
			}).get();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Zamknięcie puli wątków
			customThreadPool.shutdown();
			try {
				if (!customThreadPool.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
					customThreadPool.shutdownNow();
				}
			} catch (InterruptedException e) {
				customThreadPool.shutdownNow();
			}
		}
    }
}
