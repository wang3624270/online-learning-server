package org.octopus.auth.restapi;

import java.util.ArrayList;


import org.starfish.login_users.UserTokenServerSide;

public class UserTokenPool {
	private static ArrayList<UserTokenServerSide> AR_WECHAT_USERTOKEN;
	private static Guard guard;
	private static ReToken retoken;

	public UserTokenPool() {
		guard = new Guard();
		retoken = new ReToken();
		AR_WECHAT_USERTOKEN = new ArrayList<>();
	}

	// 起
	public boolean testGo() {
		try {
			retoken.start();
			guard.start();

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// 重置
	public boolean reState() {

		try {
			// 先打断守护线程
			if (guard != null && guard.isAlive())
				guard.interrupt();

			if (retoken != null && retoken.isAlive())
				retoken.interrupt();

			// 清除数据
			AR_WECHAT_USERTOKEN = new ArrayList<>();
			retoken = new ReToken();
			guard = new Guard();

			guard.start();
			retoken.start();

		} catch (Exception e) {
			return false;
		}
		return true;

	}

	// 守护线程，3S周期检查维护token线程状态，在必要时重置token线程
	static class Guard extends Thread {
		@Override
		public void run() {
			while (true) {
				if (retoken.isAlive())
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else {
					try {
						if(retoken!=null)
						    retoken.interrupt();
					} catch (Exception e) {

					}
					retoken=new ReToken();
					retoken.start();
				}

			}

		}

	}

	// token线程,
	static class ReToken extends Thread {
		@Override
		public void run() {

		}
	}
}
