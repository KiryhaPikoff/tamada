package ul.ulstu.tamada.rest;


import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Log
@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<String> getHello()
    {
        return new ResponseEntity<>("Hello from tamada da backend!", HttpStatus.OK);
    }

    @GetMapping("/lab3")
    public ResponseEntity<List<IterationDto>> getThird(
           @RequestParam("e") BigDecimal e,
           @RequestParam("x0") BigDecimal x0,
           @RequestParam("h") BigDecimal h,
           @RequestParam("addScale") Integer addScale
    ) {
        var iterations = new LinkedList<IterationDto>();
        var iteration_num = 1;
        BigDecimal xn_1 = null;
        var x = x0;
        BigDecimal diff_m = null;
        iterations.add(IterationDto.builder().result(x0).difference("Не вычисляется").iteration(0).build());
        while (diff_m == null || diff_m.compareTo(e) != -1) {
            var iteration = new IterationDto();

            var fx = BigDecimal.valueOf(0.5 * Math.exp(x.negate().multiply(x).doubleValue())).add(BigDecimal.valueOf(x.multiply(BigDecimal.valueOf(Math.cos(x.doubleValue()))).doubleValue()));
            var x_h = x.add(h);
            var fx_h = BigDecimal.valueOf(0.5 * Math.exp(x_h.negate().multiply(x_h).doubleValue())).add(BigDecimal.valueOf(x_h.multiply(BigDecimal.valueOf(Math.cos(x_h.doubleValue()))).doubleValue()));


            xn_1 = x;
            x = x.subtract(fx.multiply(h).divide(fx_h.subtract(fx), RoundingMode.HALF_UP));

            var diff = x.subtract(xn_1).abs().setScale(e.scale() + addScale, RoundingMode.CEILING);
            diff_m = diff;

            iteration.setIteration(iteration_num);
            iteration_num++;
            iteration.setDifference(diff.setScale(e.scale() + addScale, RoundingMode.CEILING).toString());
            iteration.setResult(x.setScale(e.scale(), RoundingMode.CEILING));

            iterations.add(iteration);
        }
        return new ResponseEntity<>(iterations, HttpStatus.OK);
    }

    @GetMapping("/lab4")
    public ResponseEntity<List<Iteration4>> getFourth(
            @RequestParam("e") BigDecimal e,
            @RequestParam("x1") BigDecimal x1,
            @RequestParam("x2") BigDecimal x2,
            @RequestParam("zn") boolean zn
    ) {
        var alpha = new BigDecimal("0.3");
        var iterations = new LinkedList<Iteration4>();

        var nullIteration = new Iteration4();
        nullIteration.setIteration(0);
        nullIteration.setX1(x1);
        nullIteration.setX2(x2);
        nullIteration.setFi1(fi1(x1, x2));
        nullIteration.setFi2(fi2(x1, x2));
        nullIteration.setNorma(null);

        iterations.add(nullIteration);

        var _lastX1 = nullIteration.getX1();
        var _lastX2 = nullIteration.getX2();

        while (iterations.getLast().getNorma() == null || iterations.getLast().getNorma().compareTo(e) != -1) {
            var iteration = new Iteration4();

            var _x1 = calculateX(iterations.getLast().getX1(), iterations.getLast().getFi1(), alpha);
            iteration.setX1(zn ? _x1.setScale(e.scale(), RoundingMode.CEILING) : _x1);

            var _x2 = calculateX(iterations.getLast().getX2(), iterations.getLast().getFi2(), alpha);
            iteration.setX2(zn ? _x2.setScale(e.scale(), RoundingMode.CEILING) : _x2);

            iteration.setFi1(fi1(_x1, _x2));
            iteration.setFi2(fi2(_x1, _x2));
            iteration.setNorma(calcNorma(_x1.subtract(_lastX1), _x2.subtract(_lastX2)));
            iteration.setIteration(iterations.getLast().getIteration() + 1);

            _lastX1 = _x1;
            _lastX2 = _x2;

            iterations.add(iteration);
        }

        return new ResponseEntity<>(iterations, HttpStatus.OK);
    }

    private BigDecimal calculateX(BigDecimal x, BigDecimal fi, BigDecimal alpha) {
        return x.subtract(fi.multiply(alpha));
    }

    private BigDecimal calcNorma(BigDecimal dx1, BigDecimal dx2) {
        return BigDecimal.valueOf(
                Math.max(
                        Math.abs(dx1.doubleValue()),
                        Math.abs(dx2.doubleValue())
                )
        );
    }

    private BigDecimal fi1(BigDecimal x1, BigDecimal x2) {
        return f1(x1,x2).multiply(BigDecimal.valueOf(2))
                .add(f2(x1,x2).multiply(BigDecimal.valueOf(2)).multiply(BigDecimal.valueOf(Math.sin(x1.doubleValue()))));
    }

    private BigDecimal fi2(BigDecimal x1, BigDecimal x2) {
        return f1(x1,x2).multiply(BigDecimal.valueOf(2)).multiply(BigDecimal.valueOf(Math.sin(x2.doubleValue() - 1)).negate())
                .add(f2(x1,x2).multiply(BigDecimal.valueOf(2)));
    }

    private BigDecimal f1(BigDecimal x1, BigDecimal x2) {
        return BigDecimal.valueOf(Math.cos(x2.subtract(BigDecimal.valueOf(1)).doubleValue()))
                .add(x1).subtract(BigDecimal.valueOf(0.5));
    }

    private BigDecimal f2 (BigDecimal x1, BigDecimal x2) {
        return x2.subtract(BigDecimal.valueOf(Math.cos(x1.doubleValue()))).subtract(BigDecimal.valueOf(3));
    }
}
