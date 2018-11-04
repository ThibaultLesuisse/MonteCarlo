import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.random.JDKRandomGenerator;

public class MonteCarlo {
    private JButton button1;
    private JTextField txtIteraties;
    public  JPanel PanelMain;
    private JLabel fieldStandaardAfwijking;
    private JTextField txtStandaardAfwijkingAantal;
    private JTextField txtMeanAantal;
    private JLabel fieldMean;
    private JTextField txtStandaardAfwijkingPrijs;
    private JTextField txtMeanPrijs;
    private JLabel lblMin;
    private JLabel lblMax;
    private JLabel lblAverageRevenue;
    private JLabel lblProfitChance;
    private JTextField txtKostenMin;
    private JTextField txtKostenMax;
    private JLabel lblMinProfit;
    private JLabel lblMaxProfit;
    private JCheckBox toonWaardenCheckBox;
    private JTable table1;
    private JScrollPane Scroll;
    private JTextField txtKostenModus;
    private JLabel lblKostenMin;
    private JLabel lblKostenMax;
    private JLabel lblAverageCost;
    private JLabel lblAverageProfit;
    private JLabel lblBin1;
    private JLabel lblBin2;
    private JLabel lblBin3;
    private JLabel lblBin4;
    private JLabel lblBin5;
    private JLabel lblBin6;
    private JLabel lblBin7;
    private JLabel lblBin8;
    private JLabel lblBin9;
    private JLabel lblBin10;
    private JLabel lblBinValue1;
    private JLabel lblBinValue2;
    private JLabel lblBinValue3;
    private JLabel lblBinValue4;
    private JLabel lblBinValue5;
    private JLabel lblBinValue6;
    private JLabel lblBinValue7;
    private JLabel lblBinValue8;
    private JLabel lblBinValue9;
    private JLabel lblBinValue10;
    private JLabel lblIntro;
    private  String[] columnNames = { "Aantal", "Prijs", "Omzet", "Kosten", "Winst" };

    private JDKRandomGenerator rgen = new JDKRandomGenerator();
    public MonteCarlo() {

        table1.setVisible(false);
        Scroll.setVisible(false);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    NormalDistribution normInvAantal = new NormalDistribution(rgen, Double.parseDouble(txtMeanAantal.getText()), Double.parseDouble(txtStandaardAfwijkingAantal.getText()));
                    NormalDistribution normInvPrijs = new NormalDistribution(rgen, Double.parseDouble(txtMeanPrijs.getText()), Double.parseDouble(txtStandaardAfwijkingPrijs.getText()));
                    TriangularDistribution triaKosten = new TriangularDistribution(rgen,Double.parseDouble(txtKostenMin.getText()), Double.parseDouble(txtKostenModus.getText()), Double.parseDouble(txtKostenMax.getText()) );

                    double[] revenue = new double[Integer.parseInt(txtIteraties.getText())];
                    double[] cost = new double[Integer.parseInt(txtIteraties.getText())];
                    String[][] data = new String[Integer.parseInt(txtIteraties.getText())][5];

                    for(int x = 0; x < Integer.parseInt(txtIteraties.getText()); x++) {

                        double normInvAantalSample = normInvAantal.sample();
                        double normInvPrijsSample = normInvPrijs.sample();

                        cost[x] = triaKosten.sample();
                        revenue[x] = (normInvAantalSample * normInvPrijsSample);
                        double profit = revenue[x] - cost[x];
                        if( toonWaardenCheckBox.isSelected()){
                            data[x][0] =  Long.toString(Math.round(normInvAantalSample));
                            data[x][1] =  Long.toString(Math.round(normInvPrijsSample));
                            data[x][2] =  Long.toString(Math.round(revenue[x]));
                            data[x][3] =  Long.toString(Math.round(cost[x]));
                            data[x][4] = Long.toString(Math.round(profit));
                        }
                    }
                    table1.setModel(new DefaultTableModel(data, columnNames));
                    findMinAndMax(revenue, cost);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Gelieve numerieke waarden in te geven");
                }
                catch (NumberIsTooLargeException | NumberIsTooSmallException ex){
                    JOptionPane.showMessageDialog(null, "De a, b of s zijn niet valide");
                }
            }

            private void findMinAndMax(double[] revList, double[] costlist) {
                double minRev = revList[0];
                double maxRev = revList[0];
                double sumRev = 0;
                double minCost = costlist[0];
                double maxCost = costlist[0];
                double sumCost = 0;
                double[] profit = new double[revList.length];

                for(int x = 0; x< revList.length;x++ ){
                    if(revList[x] > maxRev){
                        maxRev = revList[x];
                    }
                    if(costlist[0] > maxCost){
                        maxCost = costlist[x];
                    }
                    if(minRev > revList[x]){
                        minRev = revList[x];
                    }
                    if(minCost > costlist[x]){
                        minCost = revList[x];
                    }

                    sumRev += revList[x];
                    sumCost += costlist[x];
                    profit[x] = revList[x] - costlist[x];
                }

                double minProfit = profit[0];
                double maxProfit = profit[0];
                double belowZeroCountProfit = 0;
                double sumProfit = 0;

                for (int x = 0; x < revList.length; x++){
                    if(profit[x] > maxProfit ){
                        maxProfit = profit[x];
                    }
                    if(minProfit > profit[x]){
                        minProfit = profit[x];
                    }
                    if(profit[x] < 0){
                        belowZeroCountProfit ++;
                    }
                    sumProfit += profit[x];

                }
                double interval  = (maxProfit - minProfit) / 10;
                lblBin1.setText(Double.toString(Math.round( minProfit)) + " - " + Double.toString(Math.round( minProfit + (interval *1))));
                lblBin2.setText(Double.toString(Math.round( minProfit + (interval *1))) + " - " +  Double.toString(Math.round(minProfit + (interval *2))));
                lblBin3.setText(Double.toString(Math.round( minProfit + (interval *2))) + " - " + Double.toString(Math.round( minProfit + (interval *3))));
                lblBin4.setText(Double.toString(Math.round( minProfit + (interval *3))) + " - " + Double.toString(Math.round( minProfit + (interval *4))));
                lblBin5.setText(Double.toString(Math.round( minProfit + (interval *4))) + " - " + Double.toString(Math.round( minProfit + (interval *5))));
                lblBin6.setText(Double.toString(Math.round( minProfit + (interval *5))) + " - " + Double.toString(Math.round( minProfit + (interval *6))));
                lblBin7.setText(Double.toString(Math.round( minProfit + (interval *6))) + " - " + Double.toString(Math.round( minProfit + (interval *7))));
                lblBin8.setText(Double.toString(Math.round( minProfit + (interval *7))) + " - " + Double.toString(Math.round( minProfit + (interval *8))));
                lblBin9.setText(Double.toString(Math.round( minProfit + (interval *8))) + " - " + Double.toString(Math.round( minProfit + (interval *9))));
                lblBin10.setText(Double.toString(Math.round( minProfit + (interval *9))) + " - " + Double.toString(Math.round( minProfit + (interval *10))));

                ArrayList<Integer> intervalGroups = new ArrayList<Integer>( Collections.nCopies(10,0));
                for(double p: profit){
                    for(int x = 0; x < 10; x++){
                        if(p > minProfit + (interval * x) && p < minProfit + ( interval * (x+1))){
                            intervalGroups.set(x, intervalGroups.get(x) + 1);
                        }
                    }
                }
                double val = (intervalGroups.get(2) / profit.length)*100;
                lblBinValue1.setText(Double.toString(((double)intervalGroups.get(0) /(double) profit.length)*100) + "%");
                lblBinValue2.setText(Double.toString(((double)intervalGroups.get(1) / (double)profit.length)*100) + "%");
                lblBinValue4.setText(Double.toString(((double)intervalGroups.get(3) /(double) profit.length)*100) + "%");
                lblBinValue3.setText(Double.toString(((double)intervalGroups.get(2) / (double)profit.length)*100) + "%");
                lblBinValue5.setText(Double.toString(((double)intervalGroups.get(4) / (double)profit.length)*100) + "%");
                lblBinValue6.setText(Double.toString(((double)intervalGroups.get(5) / (double)profit.length)*100) + "%");
                lblBinValue7.setText(Double.toString(((double)intervalGroups.get(6) / (double)profit.length)*100) + "%");
                lblBinValue8.setText(Double.toString(((double)intervalGroups.get(7) / (double)profit.length)*100) + "%");
                lblBinValue9.setText(Double.toString(((double)intervalGroups.get(8) / (double)profit.length)*100) + "%");
                lblBinValue10.setText(Double.toString(((double)intervalGroups.get(9) / (double)profit.length)*100) + "%");



                double belowZeroProfit = 100 - ((belowZeroCountProfit / revList.length) * 100);
                long averageRevenue = Math.round((sumRev / revList.length));
                long averageCost = Math.round((sumCost / revList.length));
                long averageProfit = Math.round((sumProfit / revList.length));
                lblProfitChance.setText(Double.toString(belowZeroProfit) + "%");
                lblAverageRevenue.setText("€" + Long.toString(averageRevenue));
                lblAverageCost.setText("€" + Long.toString(averageCost));
                lblMin.setText("€" + Math.round(minRev));
                lblMinProfit.setText("€" + Math.round(minProfit));
                lblMaxProfit.setText("€" + Math.round(maxProfit));
                lblMax.setText("€" + Math.round(maxRev));
                lblKostenMax.setText("€" + Math.round(maxCost));
                lblKostenMin.setText("€" + Math.round(minCost));
                lblAverageProfit.setText("€" + Math.round(averageProfit));

            }
        });
        toonWaardenCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(toonWaardenCheckBox.isSelected()){
                    Scroll.setVisible(true);
                    table1.setVisible(true);
                }else{
                    table1.setVisible(false);
                    Scroll.setVisible(false);
                }
            }
        });

    }
}
