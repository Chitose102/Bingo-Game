package Bingo;

/**
 * @author 千歳真咲
 */

import java.util.ArrayList;
import java.util.Collections;

public class Bingo {

	public static void main(String[] args) {

		boolean allBingo = false;

		String card[][] = createCard();

		ArrayList<String> ball = createBall();
		ArrayList<String> existsBall = new ArrayList<String>(); //出力されたビンゴボールの値を格納していく

		int num = 0; //何回ゲームが行われたかのカウント用

		while (!allBingo) {

			int reachCount = 0;
			int bingoCount = 0;

			boolean[][] hole = new boolean[5][5]; //カードに穴が空いているか
			hole[2][2] = true;

			//ビンゴボール
			System.out.println("ball[" + (num + 1) + "] : " + ball.get(num));
			System.out.println();
			existsBall.add(ball.get(num));

			//ビンゴカード出力
			for (int i = 0; i < card.length; i++) {
				for (int j = 0; j < card[i].length; j++) {

					if (i == 2 && j == 2) {
						System.out.print("FREE ");
					} //ビンゴボールの値があるか確認
					else if (existsBall.contains(card[i][j])) {
						System.out.print("(" + card[i][j] + ")" + " ");
						hole[i][j] = true; ////カードに穴が空いたらtrue
					} else {
						System.out.print(card[i][j] + "   ");
					}
				}
				System.out.println();

			}

			int[] judgeResult = bingoJudge(hole, reachCount, bingoCount);
			reachCount = judgeResult[0];
			bingoCount = judgeResult[1];

			if (bingoCount == 12) {
				allBingo = true;
			}

			System.out.println();
			System.out.println("REACH: " + reachCount);
			System.out.println("BINGO: " + bingoCount);
			System.out.println("--------------------");

			num++;
		}
	}

	/**
	 * ビンゴボール作成
	 * 
	 * @return ビンゴボール出力用のリスト
	 */
	public static ArrayList<String> createBall() {

		ArrayList<String> ball = new ArrayList<String>(); //ビンゴボール出力用

		//ビンゴボール出力用のリストを2桁の数値で作成
		for (int i = 1; i <= 75; i++) {
			ball.add(String.format("%02d", i));
		}

		Collections.shuffle(ball);

		return ball;
	}

	/**
	 * ビンゴカード作成用
	 * 
	 * @return ビンゴカード配列
	 */
	public static String[][] createCard() {

		ArrayList<String> list = new ArrayList<String>();

		//リストを2桁の数値で作成
		for (int i = 1; i <= 15; i++) {
			list.add(String.format("%02d", i));
		}

		String[][] card = new String[5][5];

		for (int i = 0; i < 5; i++) {

			Collections.shuffle(list);
			for (int j = 0; j < 5; j++) {

				//リスト内をシャッフルした値を配列に格納
				card[j][i] = list.get(j);

			}

			//リストの各要素に15を足す
			for (int j = 0; j < list.size(); j++) {
				int value = Integer.parseInt(list.get(j));
				String strValue = String.valueOf(value + 15);
				list.set(j, strValue);
			}
		}

		return card;
	}

	/**
	 * ビンゴ判定用
	 * 
	 * @param hole カードに穴が空いているか
	 * @param reachCount リーチの数
	 * @param bingoCount ビンゴの数
	 * @return 最終的なリーチの数とビンゴの数
	 */
	public static int[] bingoJudge(boolean[][] hole, int reachCount, int bingoCount) {

		//横の判定
		for (int i = 0; i < 5; i++) {

			int rowCount = 0;

			for (int j = 0; j < 5; j++) {
				if (hole[i][j]) {
					rowCount++;
				}
			}

			if (rowCount == 4) {
				reachCount++;
			} else if (rowCount == 5) {
				bingoCount++;
			}

		}

		//縦の判定
		for (int i = 0; i < 5; i++) {
			int colCount = 0;

			for (int j = 0; j < 5; j++) {
				if (hole[j][i]) {
					colCount++;
				}
			}

			if (colCount == 4) {
				reachCount++;
			} else if (colCount == 5) {
				bingoCount++;
			}
		}

		//斜めの判定
		int nanameCount1 = 0;
		int nanameCount2 = 0;

		for (int i = 0; i < 5; i++) {

			for (int j = 0; j < 5; j++) {
				if (hole[i][j]) {
					if (i == j) {
						nanameCount1++;
					}
					if ((i + j) == 4) {
						nanameCount2++;
					}
				}
			}

		}

		if (nanameCount1 == 4) {
			reachCount++;
		} else if (nanameCount1 == 5) {
			bingoCount++;
		}

		if (nanameCount2 == 4) {
			reachCount++;
		} else if (nanameCount2 == 5) {
			bingoCount++;
		}

		int count[] = { reachCount, bingoCount };

		return count;

	}
}
