package feta.objectmodels;

import feta.network.DirectedNetwork;
import feta.network.Network;
import feta.network.UndirectedNetwork;

public class RandomAttachment extends ObjectModelComponent {


    @Override
    public void calcNormalisation(Network net, int[] removed) {
        if (removed.length >= net.noNodes_) {
            normalisationConstant_=0.0;
        }
        else {
            normalisationConstant_= (double) net.noNodes_-removed.length;
        }
        tempConstant_=normalisationConstant_;
    }

    public void calcNormalisation(UndirectedNetwork net, int[] removed){}
    public void calcNormalisation(DirectedNetwork net, int[] removed){}

    @Override
    public double calcProbability(UndirectedNetwork net, int node) {
        if (tempConstant_==0)
            return 0.0;
        return 1.0/tempConstant_;
    }

    public double calcProbability(DirectedNetwork net, int node) {
        if (tempConstant_==0)
            return 0.0;
        return 1.0/tempConstant_;
    }

    @Override
    public void updateNormalisation(UndirectedNetwork net, int[] removed) {
        if (removed.length==0) {
            tempConstant_=normalisationConstant_;
            return;
        }
        tempConstant_-= 1;
    }

    @Override
    public String toString() {
        return "Random";
    }
}
