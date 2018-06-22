set terminal png truecolor
set title "Sorting Algorithms vs Time Taken to sort"
set output 'barchart.png'
set xlabel ""
set ylabel "Seconds to sort"
set yrange [0:]
set boxwidth 0.75
set grid
set boxwidth 0.95 relative
set style fill  solid 0.5 noborder
plot "plot.out" using 2:xtic(1) with boxes lc rgb "red" title ""
unset xtics
