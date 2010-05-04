package net.yura.domination.mobile;

import net.yura.domination.engine.Risk;
import net.yura.domination.engine.RiskUtil;
import net.yura.mobile.gui.DesktopPane;
import net.yura.mobile.gui.Midlet;
import net.yura.mobile.gui.components.Button;
import net.yura.mobile.gui.components.Frame;
import net.yura.mobile.gui.plaf.MetalLookAndFeel;

public class DominationMidlet extends Midlet {

    @Override
    public void initialize(DesktopPane rootpane) {
        rootpane.setLookAndFeel( new MetalLookAndFeel() );

        RiskUtil.streamOpener = new RiskMiniIO();

        Risk risk = new Risk("luca.map","risk.cards");



        GameFrame mainMenu = new GameFrame(risk);

        mainMenu.setMaximum(true);
        mainMenu.setVisible(true);

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        risk.parser("newgame");
        risk.parser("newplayer ai hard blue bob");
        risk.parser("newplayer ai hard red fred");
        risk.parser("newplayer ai hard green greg");
        risk.parser("startgame domination increasing");

    }

    @Override
    public DesktopPane makeNewRootPane() {
        return new DesktopPane(this, -1, null);
    }

}