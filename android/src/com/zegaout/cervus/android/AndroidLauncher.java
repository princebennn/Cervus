package com.zegaout.cervus.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.zegaout.cervus.Cervus;
import com.zegaout.gameworld.GameWorld;
import com.zegaout.helpers.IGoogleServices;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices{
	
	private GameHelper _gameHelper;
	private final static int REQUEST_CODE_UNUSED = 9002;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Create the GameHelper
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(false);
		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			
			@Override
			public void onSignInSucceeded() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSignInFailed() {
				// TODO Auto-generated method stub
				
			}
		};
		_gameHelper.setup(gameHelperListener);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Cervus(this), config);
	}

	@Override
	protected void onStart() {
		super.onStart();
		_gameHelper.onStart(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		_gameHelper.onStop();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void signIn() {
		try {runOnUiThread(new Runnable() {
				@Override
				public void run() {
					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log in failed" + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try {runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_gameHelper.signOut();
			}
		});
	} catch (Exception e) {
		Gdx.app.log("MainActivity", "Log out failed" + e.getMessage() + ".");
	}
	}

	@Override
	public void rateGame() {
		String str = "https://play.google.com/store/apps/details?id=com.zegaout.cervus";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void submitScore(long score) {
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
		} else {
			//signIn();
			//submitScore(score);
		}
	}

	@Override
	public void showScores() {
		if (isSignedIn() == true) {
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		} else {
			//signIn();
			//showScores();
		}
	}

	@Override
	public boolean isSignedIn() {
		return _gameHelper.isSignedIn();
	}
	
	@Override
	public void getScore(final GameWorld world) {
	    Games.Leaderboards.loadCurrentPlayerLeaderboardScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
	        @Override
	        public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
	            if (isScoreResultValid(scoreResult)) {
	                // here you can get the score like this
	                 world.setHighScore(scoreResult.getScore().getRawScore());
	            }
	        }
	    });
	}

	private boolean isScoreResultValid(final Leaderboards.LoadPlayerScoreResult scoreResult) {
	    return scoreResult != null && GamesStatusCodes.STATUS_OK == scoreResult.getStatus().getStatusCode() && scoreResult.getScore() != null;
	}
}
