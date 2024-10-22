package dk.martinersej.liarsbar.bench;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BenchHandler {

    private static final BenchHandler instance = new BenchHandler();
    private final List<Bench> benches = new ArrayList<>();

    public static BenchHandler getInstance() {
        return instance;
    }

    public List<Bench> getOccupiedBenches() {
        return benches.stream().filter(Bench::isSitting).collect(Collectors.toList());
    }

    public void stopSitting(Bench bench) {
        bench.stopSitting();
    }

    public void startSitting(Bench bench) {
        bench.startSitting();
    }

    public void removeBench(Bench bench) {
        benches.remove(bench);
    }

    public void addBench(Bench bench) {
        if (!benches.contains(bench)) benches.add(bench);
    }

    public boolean isOccupied(Bench bench) {
        return benches.contains(bench) && bench.isSitting();
    }
}
