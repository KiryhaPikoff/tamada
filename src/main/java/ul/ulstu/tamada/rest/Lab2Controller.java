package ul.ulstu.tamada.rest;

import ch.obermuhlner.math.big.BigDecimalMath;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

@RestController
public class Lab2Controller {

    private static final BigDecimal[][] MATRIX = {
            { BigDecimal.valueOf(3.345), BigDecimal.valueOf(0.329), BigDecimal.valueOf(0.365), BigDecimal.valueOf(0.203), BigDecimal.valueOf(0.305) },
            { BigDecimal.valueOf(0.125), BigDecimal.valueOf(4.210), BigDecimal.valueOf(0.402), BigDecimal.valueOf(0.520), BigDecimal.valueOf(0.283) },
            { BigDecimal.valueOf(0.314), BigDecimal.valueOf(0.251), BigDecimal.valueOf(4.531), BigDecimal.valueOf(0.168), BigDecimal.valueOf(0.680) },
            { BigDecimal.valueOf(0.197), BigDecimal.valueOf(0.512), BigDecimal.valueOf(0.302), BigDecimal.valueOf(2.951), BigDecimal.valueOf(0.293) }
    };

    @GetMapping("/lab2")
    public ResponseEntity<Resp2> getSecond(
            @RequestParam("e") BigDecimal e
    ) {
        var response = new Resp2();

        var preobraz = new BigDecimal[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                preobraz[i][j] = MATRIX[i][j].divide(j == 4 ? MATRIX[i][i] : MATRIX[i][i].negate(), RoundingMode.DOWN)
                        .setScale(MATRIX[0][0].scale() + 1);
            }
        }
        response.setPreobraz(preobraz);

        var _nevyazki = new BigDecimal[][] {
                { preobraz[0][4], preobraz[0][1], preobraz[0][2], preobraz[0][3] },
                { preobraz[1][4], preobraz[1][0], preobraz[1][2], preobraz[1][3] },
                { preobraz[2][4], preobraz[2][0], preobraz[2][1], preobraz[2][3] },
                { preobraz[3][4], preobraz[3][0], preobraz[3][1], preobraz[3][2] }
        };
        response.setNevyazky(_nevyazki);

        var iterations = new LinkedList<Iter2>();

        var nullIter = new Iter2();
        nullIter.setX1(BigDecimal.ZERO.setScale(e.scale(), RoundingMode.DOWN));
        nullIter.setX2(BigDecimal.ZERO.setScale(e.scale(), RoundingMode.DOWN));
        nullIter.setX3(BigDecimal.ZERO.setScale(e.scale(), RoundingMode.DOWN));
        nullIter.setX4(BigDecimal.ZERO.setScale(e.scale(), RoundingMode.DOWN));
        nullIter.setR1(
                _nevyazki[0][0]
                        .subtract(nullIter.getX1())
                        .add(_nevyazki[0][1].multiply(nullIter.getX2()))
                        .add(_nevyazki[0][2].multiply(nullIter.getX3()))
                        .add(_nevyazki[0][3].multiply(nullIter.getX4()))

        );
        nullIter.setR2(
                _nevyazki[1][0]
                        .subtract(nullIter.getX2())
                        .add(_nevyazki[1][1].multiply(nullIter.getX1()))
                        .add(_nevyazki[1][2].multiply(nullIter.getX3()))
                        .add(_nevyazki[1][3].multiply(nullIter.getX4()))

        );
        nullIter.setR3(
                _nevyazki[2][0]
                        .subtract(nullIter.getX3())
                        .add(_nevyazki[2][1].multiply(nullIter.getX1()))
                        .add(_nevyazki[2][2].multiply(nullIter.getX2()))
                        .add(_nevyazki[2][3].multiply(nullIter.getX4()))

        );
        nullIter.setR4(
                _nevyazki[3][0]
                        .subtract(nullIter.getX4())
                        .add(_nevyazki[3][1].multiply(nullIter.getX1()))
                        .add(_nevyazki[3][2].multiply(nullIter.getX2()))
                        .add(_nevyazki[3][3].multiply(nullIter.getX3()))

        );
        nullIter.setNorma(this.max(nullIter.getR1(), nullIter.getR2(), nullIter.getR3(), nullIter.getR4()).setScale(e.scale(), RoundingMode.DOWN));
        iterations.add(nullIter);

        var last = 3;
        while (iterations.getLast().getNorma().compareTo(e) != -1) {
            var iter = new Iter2();

            iter.setX1(last == 1 ? iterations.getLast().getX1().add(iterations.getLast().getR1()).setScale(e.scale(), RoundingMode.DOWN) : iterations.getLast().getX1());
            iter.setX2(last == 2 ? iterations.getLast().getX2().add(iterations.getLast().getR2()).setScale(e.scale(), RoundingMode.DOWN) : iterations.getLast().getX2());
            iter.setX3(last == 3 ? iterations.getLast().getX3().add(iterations.getLast().getR3()).setScale(e.scale(), RoundingMode.DOWN) : iterations.getLast().getX3());
            iter.setX4(last == 4 ? iterations.getLast().getX4().add(iterations.getLast().getR4()).setScale(e.scale(), RoundingMode.DOWN) : iterations.getLast().getX4());

            iter.setR1(last == 1 ? BigDecimal.ZERO :
                    _nevyazki[0][0]
                            .subtract(iter.getX1())
                                    .add(_nevyazki[0][1].multiply(iter.getX2()))
                                    .add(_nevyazki[0][2].multiply(iter.getX3()))
                                    .add(_nevyazki[0][3].multiply(iter.getX4()))

            );
            iter.setR2(last == 2 ? BigDecimal.ZERO :
                    _nevyazki[1][0]
                            .subtract(iter.getX2())
                                    .add(_nevyazki[1][1].multiply(iter.getX1()))
                                    .add(_nevyazki[1][2].multiply(iter.getX3()))
                                    .add(_nevyazki[1][3].multiply(iter.getX4()))

            );
            iter.setR3(last == 3 ? BigDecimal.ZERO :
                    _nevyazki[2][0]
                            .subtract(iter.getX3())
                                    .add(_nevyazki[2][1].multiply(iter.getX1()))
                                    .add(_nevyazki[2][2].multiply(iter.getX2()))
                                    .add(_nevyazki[2][3].multiply(iter.getX4()))

            );
            iter.setR4(last == 4 ? BigDecimal.ZERO :
                    _nevyazki[3][0]
                            .subtract(iter.getX4())
                                    .add(_nevyazki[3][1].multiply(iter.getX1()))
                                    .add(_nevyazki[3][2].multiply(iter.getX2()))
                                    .add(_nevyazki[3][3].multiply(iter.getX3()))

            );
            var max = this.max(iter.getR1(), iter.getR2(), iter.getR3(), iter.getR4());
            if (max.compareTo(iter.getR1()) == 0) last = 1;
            if (max.compareTo(iter.getR2()) == 0) last = 2;
            if (max.compareTo(iter.getR3()) == 0) last = 3;
            if (max.compareTo(iter.getR4()) == 0) last = 4;
            iter.setNorma(max);
            iterations.add(iter);
        }

        response.setIterations(iterations);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private BigDecimal max(BigDecimal r1, BigDecimal r2, BigDecimal r3, BigDecimal r4) {
        var m1 = Math.max(r1.doubleValue(), r2.doubleValue());
        var m2 = Math.max(m1, r3.doubleValue());
        return BigDecimal.valueOf(Math.max(m2, r4.doubleValue()));
    }
}
