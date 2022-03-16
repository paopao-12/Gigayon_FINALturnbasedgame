package com.example.gigayon_finalturn_basedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    TextView txtHeroName,txtMonsName,txtHeroHP,txtMonsHP,txtHeroMP,txtMonsMP,txtHeroDPS,txtMonsDPS,txtLog;
    Button btnNextTurn;
    ImageButton skill1,skill2;


    //Hero Stats
    String heroName = "Alfonso";
    int heroHP = 3500;
    int heroMP = 1400;
    int heroMinDamage = 200;
    int heroMaxDamage = 450;

    //Monster Stats
    String monsName = "Guzma";
    int monsterHP = 3100;
    int monsterMP = 790;
    int monsterMinDamage = 100;
    int monsterMaxDamage = 299;
    //Game Turn
    int turnNumber= 1;

    boolean disabledstatus = false;
    int statuscounter = 0;
    int buttoncounter = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //XML ids for text and button

        txtHeroName = findViewById(R.id.txtHeroName);
        txtMonsName = findViewById(R.id.txtMonsName);
        txtHeroHP = findViewById(R.id.txtHeroHP);
        txtMonsHP = findViewById(R.id.txtMonsHP);
        txtHeroMP = findViewById(R.id.txtHeroMP);
        txtMonsMP = findViewById(R.id.txtMonsMP);
        btnNextTurn = findViewById(R.id.btnNxtTurn);
        txtHeroDPS = findViewById(R.id.txtHeroDPS);
        txtMonsDPS = findViewById(R.id.txtMonsDPS);

        txtLog = findViewById(R.id.txtCombatLog);

        txtHeroName.setText(heroName);
        txtHeroHP.setText(String.valueOf(heroHP));
        txtHeroMP.setText(String.valueOf(heroMP));

        txtMonsName.setText(monsName);
        txtMonsHP.setText(String.valueOf(monsterHP));
        txtMonsMP.setText(String.valueOf(monsterMP));

        txtHeroDPS.setText(String.valueOf(heroMinDamage)+ " ~ "+ String.valueOf(heroMaxDamage));
        txtMonsDPS.setText(String.valueOf(monsterMinDamage)+ " ~ "+ String.valueOf(monsterMaxDamage));


        skill1 = findViewById(R.id.btnSkill1);
        skill2 = findViewById(R.id.btnSkill2);




        //button onClick Listener
        btnNextTurn.setOnClickListener(this);
        skill1.setOnClickListener(this);
        skill2.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        txtLog.findViewById(R.id.txtCombatLog);

        Random randomizer = new Random();
        int herodps = randomizer.nextInt(heroMaxDamage - heroMinDamage) + heroMinDamage ;
        int monsdps = randomizer.nextInt(monsterMaxDamage - monsterMinDamage) + monsterMinDamage ;

        int bleed = randomizer.nextInt(5);

        if(bleed==1){
            monsterHP = monsterHP -(heroMaxDamage + 50);
        }

        if(turnNumber % 2 != 1){//if it is enemy's turn, disable button
            skill1.setEnabled(false);
        }
        else if(turnNumber%2 == 1){
            skill1.setEnabled(true);
        }

        if(buttoncounter>0){
            skill1.setEnabled(false);
            // buttoncounter--;
        }
        else if(buttoncounter==0){
            skill1.setEnabled(true);
        }
        //skill2 button conditions
        if(turnNumber% 2 != 1){
            skill2.setEnabled(false);
        }
        else if(turnNumber%2 == 1){
            skill2.setEnabled(true);
        }
        if(buttoncounter>0){
            skill2.setEnabled(false);
            buttoncounter--;
        }
        else if(buttoncounter==0){
            skill2.setEnabled(true);
        }

        //skill 2

        switch (v.getId()) {

            case R.id.btnSkill2://bleed

                monsterHP = monsterHP - (heroMinDamage + 110);
                turnNumber++;
                txtMonsHP.setText(String.valueOf(monsterHP));
                btnNextTurn.setText("Next Turn(" + turnNumber + ")");
                txtLog.setText(" Ally " + heroName + " afflicted "+ monsName + " for " + (heroMaxDamage + 110) + " and bled for 2 turns ");

                disabledstatus = true;
                statuscounter = 2;


                //Condition
                if (monsterHP < 0) {//even
                    txtLog.setText( " Ally " + heroName + " afflicted "+ monsName + " for " + (heroMaxDamage + 110) + " pure damage " + heroName + " WON!");
                    heroHP = 3500;
                    monsterHP = 3100;
                    turnNumber = 1;
                    btnNextTurn.setText("Play Again");
                    if(statuscounter>0){ //bleeding wil be reduced while enemy is bleeding
                        statuscounter--;
                        if(statuscounter==0){
                            disabledstatus=false;
                        }
                }
                buttoncounter=10;
                buttoncounter--;



                break;

            // Skill 1
                    switch(v.getId()) {
                        case R.id.btnSkill1:

                            monsterHP = monsterHP - 250;
                            turnNumber++;
                            txtMonsHP.setText(String.valueOf(monsterHP));
                            btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                            txtLog.setText("Our Hero "+String.valueOf(heroName) +" used stun!. It dealt "+String.valueOf(250) + " damage to the enemy. The enemy is stunned for 4 turns");

                            disabledstatus = true;
                            statuscounter = 4;

                            if(monsterHP < 0){ //even
                                txtLog.setText("Our Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy. The player is victorious!");
                                heroHP = 3500;
                                monsterHP = 3000;
                                turnNumber= 1;
                                btnNextTurn.setText("Reset Game");
                            }
                            buttoncounter=12;


                            break;
                        case R.id.btnNxtTurn:
                            //

                            if(turnNumber % 2 == 1){ //odd
                                monsterHP = monsterHP - herodps;
                                turnNumber++;
                                txtMonsHP.setText(String.valueOf(monsterHP));
                                btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                                txtLog.setText("Our Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy.");

                                if(monsterHP < 0){ //even
                                    txtLog.setText("Our Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy. The player is victorious!");
                                    heroHP = 3500;
                                    monsterHP = 3100;
                                    turnNumber= 1;
                                    buttoncounter=0;
                                    btnNextTurn.setText("Reset Game");
                                }

                                buttoncounter--;
                            }
                            else if(turnNumber%2 != 1){ //even

                                if(disabledstatus==true){
                                    txtLog.setText("The enemy is still stunned for "+String.valueOf(statuscounter)+ "turns");
                                    statuscounter--;
                                    turnNumber++;
                                    btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");
                                    if(statuscounter==0){
                                        disabledstatus=false;
                                    }
                                }
                                else{
                                    heroHP = heroHP - monsdps;
                                    turnNumber++;
                                    txtHeroHP.setText(String.valueOf(heroHP));
                                    btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                                    txtLog.setText("The monster "+String.valueOf(monsName)+" dealt "+String.valueOf(monsdps)+ " damage to the enemy.");

                                    if(heroHP < 0){
                                        txtLog.setText("The monster "+String.valueOf(monsName)+" dealt "+String.valueOf(monsdps)+ " damage to the enemy. Game Over");
                                        heroHP = 3500;
                                        monsterHP = 3100;
                                        turnNumber= 1;
                                        buttoncounter=0;
                                        btnNextTurn.setText("Reset Game");
                                    }
                                }
                                // buttoncounter--;
                            }
                            break;
                    }
                }

                    }
                }

        }