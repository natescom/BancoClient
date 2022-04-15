package com.nathan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("view/layout/pag_principal.fxml"));
    primaryStage.setTitle("Entrar");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }


  public static void main(String[] args) throws Exception {
    launch(args);
//      consoleCliente();
  }

  public static void consoleCliente()throws Exception{
    Socket s = new Socket("localhost", 60000);
    Scanner teclado = new Scanner(System.in);
    PrintWriter serverSend = new PrintWriter(s.getOutputStream(), true);
    Scanner serverRecieve = new Scanner(s.getInputStream());
        /*
        1 = FAZER LOGIN
        2 = SELECIONAR NO MENU
         */
    int state = 1;
    System.out.println(ConsoleUI.BEMVINDO);
    do {
      switch (state) {
        case 1:
          System.out.println(ConsoleUI.LOGIN);
          serverSend.println(1);
          System.out.print(ConsoleUI.CAMPOLOGIN);
          serverSend.println(teclado.next());
          System.out.print(ConsoleUI.CAMPOSENHA);
          serverSend.println(teclado.next());
          break;
        case 2:
          System.out.print(ConsoleUI.MENUOPCO);
          int op = teclado.nextInt();
//          System.out.println("OPÇÃO SELECIONADA: "+op);
          serverSend.println(op + 1);
          switch (op) {
            case 1:
              System.out.println(ConsoleUI.SAQUE);
              System.out.print(ConsoleUI.CAMPOVALOR);
              serverSend.println(teclado.nextDouble());
              break;
            case 2:
              System.out.println(ConsoleUI.DEPOS);
              System.out.print(ConsoleUI.CAMPOVALOR);
              double valor = teclado.nextDouble();
//              System.out.println("Enviando valor: "+valor);
              serverSend.println(valor);
              break;
            case 3:
              System.out.println(ConsoleUI.CONSU);
              System.out.println(ConsoleUI.LABELSALDO + serverRecieve.nextLine());
              break;
            case 5:
              System.out.println(ConsoleUI.EXTRA);
              int tam = Integer.parseInt(serverRecieve.nextLine());
              for (int i = 0; i < (tam * 4); i++) {
                System.out.println(serverRecieve.nextLine());
              }
              break;
          }
          break;
        default:
      }
      String mensagemDoServidor = serverRecieve.nextLine();
      String nextState = serverRecieve.nextLine();
      state = Integer.parseInt(nextState);
      System.out.println(mensagemDoServidor);
    } while (state != 0);
    s.close();
  }

}
