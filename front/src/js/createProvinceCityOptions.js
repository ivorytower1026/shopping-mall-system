//用于选择地址所需要的options
import { province, city, area } from "province-city-china/data";

export function getOptions() {
    const options = []

    for (const i in province) {
        var o1 = new Object()
        o1.value = province[i].name  //省份编号
        o1.label = province[i].name      //省份名称
        o1.children = []
        for (const j in city) {
            var o2 = new Object()
            if (city[j].province === province[i].province) {
                o2.value = city[j].name   //城市编号
                o2.label = city[j].name    //城市名称
                o2.children = []
                o1.children.push(o2)

                for (const k in area) {
                    var o3 = new Object()
                    if (area[k].city === city[j].city && area[k].province === province[i].province) {
                        o3.value = area[k].name   //area编号
                        o3.label = area[k].name    //area名称
                        o2.children.push(o3)
                    }
                }
            }
        }
        options.push(o1)
    }
    return options
}

