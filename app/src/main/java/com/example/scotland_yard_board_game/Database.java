package com.example.scotland_yard_board_game;


import java.util.ArrayList;
import java.util.Random;

public class Database { // TODO: 4/28/2022 implement database methods
    private ArrayList<Station> Stationlist;
    private int[] DetectiveStart;
    private int[] MrXStart;

    public Database() {builddatabase();}

    private void builddatabase (){
        // TODO: 4/28/2022 build arrays for stations and start positions
        this.MrXStart = new int[] {35, 45, 51, 71, 78, 104, 106, 127, 132, 146, 166, 170, 172};
        this.DetectiveStart = new int[] {13, 26, 29, 34, 50, 53, 91, 94, 103, 112, 117, 123, 138, 141, 155, 174};
    }

    public Station getStation(int id){
        for (Station a: Stationlist) {
          if( a.getId() == id){
              return a;
          }
        }
        return null;
    }

    public int[] getRandomStart(int numPlayers){
        // TODO: 4/30/2022 Test implementation 
        int[] temp;
        int[] Start = new int[numPlayers];

        //MrX Start
        temp = this.MrXStart;
        Random random = new Random();
        int number = random.nextInt(temp.length-1);
        Start[0] = temp[number];
        
        //Detective Start
        temp = DetectiveStart;
        for (int i = 1 ; i<=numPlayers; i++){
            number = random.nextInt(temp.length-1);
            if (temp[number]!=0){
                Start[i] = temp[number];
            }else{
                while(temp[number]==0){
                    number = random.nextInt(temp.length-1);
                    Start[i] = temp[number];
                }
            }
            temp[number] = 0;
            
        }

        return Start;
    }

}
