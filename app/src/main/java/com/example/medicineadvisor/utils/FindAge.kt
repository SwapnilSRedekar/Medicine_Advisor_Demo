package com.example.medicineadvisor.utils

import java.text.SimpleDateFormat

object FindAge {

    fun calculateAge(selDay:Int,selMonth:Int,selYear:Int,curDate:Int,curMonth:Int,curYear:Int) : Int{

        var currentDate = "$curDate-$curMonth-$curYear"
        var selectedDate = "$selDay-$selMonth-$selYear"

        var leapYears = 0
        var extraDay = 0

        for (year in selYear..curYear){
            if (year == curYear || year == selYear) {
                if ((year%4==0) && (year%100!=0) || (year%400==0)){
                    when (year)  {
                        curYear ->{
                            if (curMonth >2){
                                leapYears++
                                extraDay++
                            }
                        }
                        selYear->{
                            if (selMonth<=2){
                                leapYears++
                                extraDay++
                            }
                        }

                    }
                }else{
                }

            }else{
                if ((year%4==0) && (year%100!=0) || (year%400==0)){
                    leapYears++
                }else{
                }
            }
        }

        var sdf = SimpleDateFormat("dd-MM-yyyy")
        var parseEndDate = sdf.parse(currentDate)
        var parseStartDate = sdf.parse(selectedDate)

        var dif = parseEndDate.time - parseStartDate.time

        var usersAge = dif/(1000L * 60 * 60 * 24 * 365)

        var dayss = (dif/ (1000 * 60 * 60 * 24))% 365
        var finalDays: Int
        finalDays = if (extraDay == 2) {
            (dayss.toInt() + 1) - leapYears
        } else {
            (dayss.toInt()) - leapYears
        }

        if (finalDays<0){
            usersAge--
        }
        return usersAge.toInt()
    }
}