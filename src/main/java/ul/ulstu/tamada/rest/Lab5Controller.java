package ul.ulstu.tamada.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class Lab5Controller {

    private static final BigDecimal[] _X = {
            BigDecimal.valueOf(0.248),
            BigDecimal.valueOf(0.663),
            BigDecimal.valueOf(1.238),
            BigDecimal.valueOf(2.092),
            BigDecimal.valueOf(2.939)
    };

    private static final BigDecimal[] _Y = {
            BigDecimal.valueOf(-3.642),
            BigDecimal.valueOf(0.802),
            BigDecimal.valueOf(0.842),
            BigDecimal.valueOf(0.513),
            BigDecimal.valueOf(0.328),
    };

    private static final Integer SCALE = 20;
    private static final Integer PRINT_SCALE = 3;

    private static final BigDecimal te10 = (_Y[1].subtract(_Y[0])).divide(_X[1].subtract(_X[0]), SCALE, RoundingMode.DOWN);
    private static final BigDecimal te11 = (_Y[2].subtract(_Y[1])).divide(_X[2].subtract(_X[1]), SCALE, RoundingMode.DOWN);
    private static final BigDecimal te12 = (_Y[3].subtract(_Y[2])).divide(_X[3].subtract(_X[2]), SCALE, RoundingMode.DOWN);
    private static final BigDecimal te13 = (_Y[4].subtract(_Y[3])).divide(_X[4].subtract(_X[3]), SCALE, RoundingMode.DOWN);

    private static final BigDecimal te20 = (te11.subtract(te10)).divide(_X[2].subtract(_X[0]), SCALE, RoundingMode.DOWN);
    private static final BigDecimal te21 = (te12.subtract(te11)).divide(_X[3].subtract(_X[1]), SCALE, RoundingMode.DOWN);
    private static final BigDecimal te22 = (te13.subtract(te12)).divide(_X[4].subtract(_X[2]), SCALE, RoundingMode.DOWN);

    private static final BigDecimal te30 = (te21.subtract(te20)).divide(_X[3].subtract(_X[0]), SCALE, RoundingMode.DOWN);
    private static final BigDecimal te31 = (te22.subtract(te21)).divide(_X[4].subtract(_X[1]), SCALE, RoundingMode.DOWN);

    private static final BigDecimal te40 = (te31.subtract(te30)).divide(_X[4].subtract(_X[0]), SCALE, RoundingMode.DOWN);

    private static final BigDecimal[][] tableEndSub = {
            {_X[0], _Y[0], te10, te20, te30, te40},
            {_X[1], _Y[1], te11, te21, te31},
            {_X[2], _Y[2], te12, te22},
            {_X[3], _Y[3], te13},
            {_X[4], _Y[4]}
    };

    private static final BigDecimal linSplA1 = (_Y[1].subtract(_Y[0])).divide(_X[1].subtract(_X[0]), SCALE, RoundingMode.DOWN);
    private static final BigDecimal linSplA2 = (_Y[2].subtract(_Y[1])).divide(_X[2].subtract(_X[1]), SCALE, RoundingMode.DOWN);
    private static final BigDecimal linSplA3 = (_Y[3].subtract(_Y[2])).divide(_X[3].subtract(_X[2]), SCALE, RoundingMode.DOWN);
    private static final BigDecimal linSplA4 = (_Y[4].subtract(_Y[3])).divide(_X[4].subtract(_X[3]), SCALE, RoundingMode.DOWN);

    private static final BigDecimal[][] linSplainCoefs = {
            {linSplA1, _Y[0].subtract(_X[0].multiply(linSplA1))},
            {linSplA2, _Y[1].subtract(_X[1].multiply(linSplA2))},
            {linSplA3, _Y[2].subtract(_X[2].multiply(linSplA3))},
            {linSplA4, _Y[3].subtract(_X[3].multiply(linSplA4))}
    };

    @GetMapping("/lab5")
    public ResponseEntity<Resp5> getFifth(
            @RequestParam("x") BigDecimal x
    ) {
        var response = new Resp5();

        var _P = IntStream.range(0, _X.length)
                        .mapToObj(this::findBasisCoef)
                        .collect(Collectors.toList());

        response.setLagrCoefs(_P);

        Function<BigDecimal, BigDecimal> Ln = (dot) -> {
            var lnVal = IntStream.range(0, _X.length)
                    .mapToObj(_pi -> {
                        var pi = _P.get(_pi);
                        var mult = Stream.of(_X)
                                .filter(xi -> xi.compareTo(_X[_pi]) != 0)
                                .map(dot::subtract)
                                .reduce(BigDecimal::multiply)
                                .get();
                        return pi.multiply(mult);
                    }).reduce(BigDecimal::add);
            return lnVal.get();
        };
        response.setLnVal(Ln.apply(x).setScale(SCALE, RoundingMode.DOWN));

        Function<BigDecimal, String> LnString = (dot) -> {
            var lnVal = IntStream.range(0, _X.length)
                    .mapToObj(_pi -> {
                        var pi = _P.get(_pi);
                        var mult = Stream.of(_X)
                                .filter(xi -> xi.compareTo(_X[_pi]) != 0)
                                .map(bigDecimal -> String.format("(x-%.3f)", bigDecimal.doubleValue()))
                                .collect(Collectors.joining("*"));
                        return pi.setScale(PRINT_SCALE, RoundingMode.DOWN) + "*" + mult + (_pi == _X.length - 1 ? "" : "+");
                    }).reduce(String::concat);
            return lnVal.get();
        };
        response.setLnString(LnString.apply(x));

        response.setTableEndSub(tableEndSub);

        var te = new BigDecimal[] {_Y[0], te10, te20, te30, te40};

        Function<BigDecimal, BigDecimal> Newton = (dot) -> {
            var lnVal = IntStream.range(0, _X.length)
                    .mapToObj(i -> {
                        var tei = te[i];
                        var mult = IntStream.range(0, i)
                                .mapToObj(it -> dot.subtract(_X[it]))
                                .reduce(BigDecimal::multiply)
                                .orElse(BigDecimal.ONE);
                        return tei.multiply(mult);
                    }).reduce(BigDecimal::add);
            return lnVal.get();
        };
        response.setNewVal(Newton.apply(x).setScale(SCALE, RoundingMode.DOWN));

        Function<BigDecimal, String> NewtonString = (dot) -> {
            var lnVal = IntStream.range(0, _X.length)
                    .mapToObj(i -> {
                        var tei = te[i];
                        var mult = IntStream.range(0, i)
                                .mapToObj(it -> String.format("(x-%.3f)", _X[it].doubleValue()))
                                .collect(Collectors.joining("*"));

                        return tei.setScale(PRINT_SCALE, RoundingMode.DOWN) + (mult.equals("") ? "" : "*") + mult + (i == _X.length - 1 ? "" : "+");
                    }).reduce(String::concat);
            return lnVal.get();
        };
        response.setNewString(NewtonString.apply(x));

        response.setNewString(NewtonString.apply(x));

        response.setLinSplainCoefs(linSplainCoefs);

        Function<BigDecimal, BigDecimal> LinSplain = (dot) -> {
            var lastXi = IntStream.range(0, _X.length)
                    .filter(i -> dot.compareTo(_X[i]) == -1)
                    .dropWhile(i -> dot.compareTo(_X[i]) >= 0)
                    .findFirst();
            if (lastXi.isPresent()) {
                return linSplainCoefs[lastXi.getAsInt() - 1][0].multiply(dot)
                        .add(linSplainCoefs[lastXi.getAsInt() - 1][1]);
            }
            return BigDecimal.ONE.negate();
        };

        Function<Integer, String> LinSplainString = (num) -> String.format("%.3f*x+%.3f", linSplainCoefs[num - 1][0].doubleValue(), linSplainCoefs[num - 1][1].doubleValue());

        var splains = IntStream.range(1, _X.length)
                .mapToObj(LinSplainString::apply)
                .collect(Collectors.toList());
        response.setSplainsString(splains);

        response.setSplainVal(LinSplain.apply(x).setScale(SCALE, RoundingMode.DOWN));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private BigDecimal findBasisCoef(Integer iX) {
        var pi = Stream.of(_X)
                .filter(x -> x.compareTo(_X[iX]) != 0)
                .reduce(BigDecimal.ONE, (acc, x) -> acc.multiply(_X[iX].subtract(x)));
        var dev = BigDecimal.ONE.divide(pi, SCALE, RoundingMode.CEILING);
        return dev.multiply(_Y[iX]);
    }
}
