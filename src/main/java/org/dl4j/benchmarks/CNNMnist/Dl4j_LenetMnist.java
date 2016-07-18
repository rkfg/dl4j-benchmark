package org.dl4j.benchmarks.CNNMnist;


import org.deeplearning4j.datasets.iterator.MultipleEpochsIterator;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.dl4j.benchmarks.TestModels.LeNet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Dl4j_LenetMnist {
    private static final Logger log = LoggerFactory.getLogger(Dl4j_LenetMnist.class);
    protected static final int height = 28;
    protected static final int width = 28;
    public final static int channels = 1;
    public final static int numLabels = 10;
    public final static int trainBatchSize = 66;
    public final static int testBatchSize = 100;
    public final static int epochs = 11;
    public final static int iterations = 1;
    public final static int seed = 123;
    public final static int nCores = 32;

    public static void main(String[] args) throws Exception {
        long duration = System.currentTimeMillis();
        DataSetIterator mnistTrain = new MnistDataSetIterator(trainBatchSize,true,12345);
        DataSetIterator mnistTest = new MnistDataSetIterator(testBatchSize,false,12345);

        MultiLayerNetwork network = new LeNet(height, width, channels, numLabels, seed, iterations).init();
        network.init();

        for(int i=0; i < epochs; i++) {
            network.fit(mnistTrain);
            if (i != epochs-1) mnistTrain.reset();
        }

        Evaluation eval = network.evaluate(mnistTest);
        log.info(eval.stats());

        log.info("****************Example finished********************");
        log.info("Total time: {}", (System.currentTimeMillis() - duration));

    }
}