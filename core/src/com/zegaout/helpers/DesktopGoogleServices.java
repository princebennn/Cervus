package com.zegaout.helpers;

import com.zegaout.gameworld.GameWorld;

public class DesktopGoogleServices implements IGoogleServices{

	@Override
	public void signIn() {
		System.out.println("DestopGoogleServices: signIn()");
	}

	@Override
	public void signOut() {
		System.out.println("DestopGoogleServices: signOut()");
	}

	@Override
	public void rateGame() {
		System.out.println("DestopGoogleServices: rateGame()");
	}

	@Override
	public void submitScore(long score) {
		System.out.println("DestopGoogleServices: submitScore()");
	}

	@Override
	public void showScores() {
		System.out.println("DestopGoogleServices: showScores()");
	}

	@Override
	public boolean isSignedIn() {
		System.out.println("DestopGoogleServices: isSignedIn()");
		return false;
	}

	@Override
	public void getScore(GameWorld world) {
		System.out.println("DestopGoogleServices: getScore()");
	}

	
}
