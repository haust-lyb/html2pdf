package com.haustlyb.html2pdf.controllers;

import cn.hutool.core.util.StrUtil;
import com.haustlyb.html2pdf.component.HSResult;
import com.haustlyb.html2pdf.services.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "PDF生成", tags = "PDF生成")
@ApiIgnore
public class TemplateController {
    @Autowired
    TemplateService templateService;

    @PostMapping("/demoDemplate")
    @ResponseBody
    @ApiOperation(value = "获取初始化的模板", notes = "获取初始化模板内容")
    public String demoDemplate() {
        return "<!doctype html>\n" +
                "<html lang=\"zh\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>会诊申请书</title>\n" +
                "    <style>\n" +
                "        .jky-application {\n" +
                "            position: relative;\n" +
                "            width: 100%;\n" +
                "            padding: 20px;\n" +
                "            box-sizing: border-box;\n" +
                "            height: 150px;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            background: white;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            table-layout: fixed;\n" +
                "        }\n" +
                "\n" +
                "        td {\n" +
                "            /*自动换行*/\n" +
                "            word-break: break-all;\n" +
                "            word-wrap: break-word;\n" +
                "            padding: 3px 8px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"jky-application\">\n" +
                "    <h2 style=\"text-align: center;\">${applyHospitalName!\"河科大一附院-远程会诊申请单\"}</h2>\n" +
                "    <img style=\"position:absolute;width: 200px;right: 20px;bottom: 20px;\"\n" +
                "         src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAARYAAABZCAYAAAD7NAtCAAAK12lDQ1BJQ0MgUHJvZmlsZQAASImVlwdUU+kSgP970xsEAkgn9N5bACmhh96bqIQkkFBCTAgqYmdxBVYUFREsK7oooODqUmRREQu2RbFhX5BFQVkXCzZU9gKPsLvvvPfOm3Pmznfmzj//zH/uf85cACiBbJEoC6YCkC3MFUcFeNMTEpPouCGABmQgB2wBjc2RiJgRESEAkVn7d3l3B0BT9qblVK5/f/9fRZHLk3AAgJIRTuVKONkIdyI6yhGJcwFAHUb8+ktzRVN8HWElMVIgwr9NcfoMf5ji1GlGk6djYqJ8EKYDgCez2eJ0AMgWiJ+ex0lH8pCnerARcgVChAsQ9uDw2VyEOxC2yM7OmeJhhE2QeBEAFOR0ACP1LznT/5Y/VZafzU6X8Uxf04L3FUhEWezl/+fR/G/JzpLO7mGEKJkvDoya6h85v7uZOcEyFqaGhc+ygDsdP818aWDsLHMkPkmzzGX7BsvWZoWFzHKawJ8ly5PLipllnsQvepbFOVGyvdLEPsxZZovn9pVmxsr8fB5Llj+fHxM/y3mCuLBZlmRGB8/F+Mj8YmmUrH6eMMB7bl9/We/Zkr/0K2DJ1ubyYwJlvbPn6ucJmXM5JQmy2rg8X7+5mFhZvCjXW7aXKCtCFs/LCpD5JXnRsrW5yMc5tzZCdoYZ7KCIWQYRIBCEADqwA/bIHfQGDiAyl7csd6oRnxzRcrEgnZ9LZyK3jUdnCTlWFnQ7GztbAKbu7szn8Obu9J2EVPBzPt5xABxsECd1zsdnA9COAYBaMucz6QJA4TIAZ8o5UnHejA899cAAIpBHKlQD2kAfmABLpD4n4Aa8gB8IAuEgBiSCRYAD+CAbiMFSUADWgiJQAjaD7aAK7AX7wSFwBBwDraADnAEXwBVwHdwGD0A/GAIvwBh4ByYgCMJBFIgGqUE6kCFkDtlBDMgD8oNCoCgoEUqB0iEhJIUKoPVQCVQOVUH7oDroR+gEdAa6BPVC96ABaAR6DX2CUTAZVoK1YCPYGmbATDgYjoEXwunwEjgfLoQ3wZVwDXwYboHPwFfg23A//AIeRwEUCaWC0kVZohgoH1Q4KgmVhhKjVqGKURWoGlQjqh3VjbqJ6keNoj6isWgamo62RLuhA9GxaA56CXoVuhRdhT6EbkGfQ99ED6DH0F8xFIwmxhzjimFhEjDpmKWYIkwFphbTjDmPuY0ZwrzDYrEqWGOsMzYQm4jNwK7AlmJ3Y5uwndhe7CB2HIfDqeHMce64cBwbl4srwu3EHcadxt3ADeE+4El4Hbwd3h+fhBfi1+Er8PX4U/gb+Gf4CQKVYEhwJYQTuITlhDLCAUI74RphiDBBVCAaE92JMcQM4lpiJbGReJ74kPiGRCLpkVxIkSQBaQ2pknSUdJE0QPpIViSbkX3IyWQpeRP5ILmTfI/8hkKhGFG8KEmUXMomSh3lLOUx5YMcTc5KjiXHlVstVy3XIndD7qU8Qd5Qnim/SD5fvkL+uPw1+VEqgWpE9aGyqauo1dQT1D7quAJNwVYhXCFboVShXuGSwrAiTtFI0U+Rq1iouF/xrOIgDUXTp/nQOLT1tAO087QhJaySsRJLKUOpROmIUo/SmLKisoNynPIy5Wrlk8r9KigVIxWWSpZKmcoxlTsqn+ZpzWPO483bOK9x3o1571U1VL1UearFqk2qt1U/qdHV/NQy1baotao9Ukerm6lHqi9V36N+Xn1UQ0nDTYOjUaxxTOO+JqxpphmluUJzv+ZVzXEtba0ALZHWTq2zWqPaKtpe2hna27RPaY/o0HQ8dAQ623RO6zynK9OZ9Cx6Jf0cfUxXUzdQV6q7T7dHd0LPWC9Wb51ek94jfaI+Qz9Nf5t+l/6YgY5BqEGBQYPBfUOCIcOQb7jDsNvwvZGxUbzRBqNWo2FjVWOWcb5xg/FDE4qJp8kSkxqTW6ZYU4Zppulu0+tmsJmjGd+s2uyaOWzuZC4w323ea4GxcLEQWtRY9FmSLZmWeZYNlgNWKlYhVuusWq1eWhtYJ1lvse62/mrjaJNlc8Dmga2ibZDtOtt229d2ZnYcu2q7W/YUe3/71fZt9q8czB14Dnsc7jrSHEMdNzh2OX5xcnYSOzU6jTgbOKc473LuYygxIhiljIsuGBdvl9UuHS4fXZ1cc12Puf7hZumW6VbvNjzfeD5v/oH5g+567mz3fe79HnSPFI/vPfo9dT3ZnjWeT7z0vbhetV7PmKbMDOZh5ktvG2+xd7P3ex9Xn5U+nb4o3wDfYt8eP0W/WL8qv8f+ev7p/g3+YwGOASsCOgMxgcGBWwL7WFosDquONRbkHLQy6FwwOTg6uCr4SYhZiDikPRQODQrdGvowzDBMGNYaDsJZ4VvDH0UYRyyJ+DkSGxkRWR35NMo2qiCqO5oWvTi6PvpdjHdMWcyDWJNYaWxXnHxcclxd3Pt43/jy+P4E64SVCVcS1RMFiW1JuKS4pNqk8QV+C7YvGEp2TC5KvrPQeOGyhZcWqS/KWnRysfxi9uLjKZiU+JT6lM/scHYNezyVlbordYzjw9nBecH14m7jjvDceeW8Z2nuaeVpw+nu6VvTR/ie/Ar+qMBHUCV4lRGYsTfjfWZ45sHMyaz4rKZsfHZK9gmhojBTeC5HO2dZTq/IXFQk6l/iumT7kjFxsLhWAkkWStpylZAh6arURPqNdCDPI68678PSuKXHlyksEy67utxs+cblz/L9839YgV7BWdFVoFuwtmBgJXPlvlXQqtRVXav1VxeuHloTsObQWuLazLW/rLNZV77u7fr49e2FWoVrCge/CfimoUiuSFzUt8Ftw95v0d8Kvu3ZaL9x58avxdziyyU2JRUln0s5pZe/s/2u8rvJTWmbesqcyvZsxm4Wbr6zxXPLoXKF8vzywa2hW1u20bcVb3u7ffH2SxUOFXt3EHdId/RXhlS27TTYuXnn5yp+1e1q7+qmXZq7Nu56v5u7+8Yerz2Ne7X2luz99L3g+7v7Ava11BjVVOzH7s/b//RA3IHuHxg/1NWq15bUfjkoPNh/KOrQuTrnurp6zfqyBrhB2jByOPnw9SO+R9oaLRv3Nak0lRwFR6VHn/+Y8uOdY8HHuo4zjjf+ZPjTrmZac3EL1LK8ZayV39rfltjWeyLoRFe7W3vzz1Y/H+zQ7ag+qXyy7BTxVOGpydP5p8c7RZ2jZ9LPDHYt7npwNuHsrXOR53rOB5+/eMH/wtluZvfpi+4XOy65XjpxmXG59YrTlZarjlebf3H8pbnHqaflmvO1tusu19t75/eeuuF548xN35sXbrFuXbkddrv3Tuydu33Jff13uXeH72Xde3U/7/7EgzUPMQ+LH1EfVTzWfFzzq+mvTf1O/ScHfAeuPol+8mCQM/jiN8lvn4cKn1KeVjzTeVY3bDfcMeI/cv35gudDL0QvJkaLflf4fddLk5c//eH1x9WxhLGhV+JXk69L36i9OfjW4W3XeMT443fZ7ybeF39Q+3DoI+Nj96f4T88mln7Gfa78Yvql/Wvw14eT2ZOTIraYPT0KoBCF09IAeH0QmY0TAaAhczlxwcxsPS3QzP/ANIH/xDPz97Q4AVDbCUDcGgCivADYjagxwlTERiAa4wVge3uZ/kskafZ2M7lIrchoUjE5+QaZHXGmAHzpm5ycaJ2c/FKLFHsfgM53MzP9lDCR/wP8CwCqjt6kjq8B/5CZef8vPf7TAlkFf7N/Ais3GM1/Ac18AAAAOGVYSWZNTQAqAAAACAABh2kABAAAAAEAAAAaAAAAAAACoAIABAAAAAEAAAEWoAMABAAAAAEAAABZAAAAAAoYaBkAAEAASURBVHgB7V0HfJVV8p303nsjhUAIvffem2JDigIKgsruujYUZFexoa6IFQFBBESliRRBkN57JyShpZBeSe/J/5z7eDFCgIQ81P/P78JLXt776nz3zp05c2auUQWaaE2TgCYBTQIGlICxAY+lHUqTgCYBTQJKAppi0TqCJgFNAgaXgKZYDC5S7YCaBDQJaIpF6wOaBDQJGFwCmmIxuEi1A2oS0CSgKRatD2gS0CRgcAloisXgItUOqElAk4CmWLQ+oElAk4DBJaApFoOLVDugJgFNAppi0fqAJgFNAgaXgKZYDC5S7YCaBDQJaIpF6wOaBDQJGFwCmmIxuEi1A2oS0CSgKRatD2gS0CRgcAloisXgItUOqElAk4DpnymCktIyKS0rEyNcRHFJiRSXlgvLwxgZ4SXGYmxiLJbmZup7MzNTMTHW9OCf+by0c2sSqKkE/nDFkp2XLzn5hVICJZKalSNSUS5WFuYSl5oqpVAqVpbWYoLPsnNzxajCSLw83KB8yqFUjMTJzlrsLC3E3tZG7VPTm9S20ySgSeCPlYDRH1FBjlZJfFqmpGbmSF5piXg4OYijlYWYWZiJjZUllIaJmOG+WcquDD+MYbEYKztFpAB/l5aXS1FWlmQXFElecamUQwEZGxmJp6OduDraw8KhzaM1TQKaBP4qEriniqWgqFjiktMkIf2amFtbSUNfD7GCS2NuagQFUyHhMQmSB+slJSVVGgT6S1xismQXFklBbp50atpIiqFQCkpLpQhuUgMfL3G2tRIzc3MxhmJJz82X1NQMKSorFT93V3G1txVTU5O/ily169Ak8LeWwD1RLGVQCBeuJkkyXB0bU2NpEhwoxuUlEpeZLV7OTvLLodMSk5IhBYUFEuTvK+fPhcPlcZWcwlKxtbWWa5nXpEurxpKUkiZpOfkSm5YtllBGDQN8xB6YS7Cft9jB2vF0c5H8klJJSEqBa1UmHs4O4g4LxljDYv7WnVq7+T9fAgZXLCmwThKgGIyNjMXD1Uk8HOzk3JWrEhGfLGevxMuYfh3V3+nXcsXMzESCfDygDBxghZTjbzO8dLCPrbWlpF/LllRYJuVwi86EX4DycZeImHgRKC4XKKCG9QPEzc5KQny9JD0nTyKi48QOllGwr6dYA7fRmiYBTQJ/jgQMpljKMdhjktKBpWRIoI+7shzyC0vEzNpC1u44CLcnUXxhYYzo1VasbawVMEtlQrzEzNRUcmB5ENgtKSoSU0SDiLs42tnBddK5N2XlFcBfyiQmNVMuxcRJRna+XI5NEDu4QCEBvtI6JFAcAewmZ+VKXl4e9rUWbxfnP0eq2lk1CfzNJWAQxVIEpXA1KU3K4IIEerpKcXGJhMclSUJ8vIQG+kq5qYWkpWdI+8YNgK+YSh6wl1gooJMRV+RgRIxSSGmwTnLyC6QExxIjEzGHq+NoYyG+cG/8Pd2kc9NgadnAX3xgBTHonASL59C5SEnJK5I4uEKN/dwl0MtTGtXzwjlM5EjkFfF1cVQWkdF1IPhv/qy129ck8IdJoM6KhYrgItwcZyc78XSwlxMY0BlFpXI2/KKYIs4TAlykd4dWeC8SlZwu20+Fy7q9J+RIRJSkQDkAJIFrUwZlQnWBrSoDPAgH8XOGioDTwLeRAC8X6d8mVIZ0aiG9mjdUoecrOPcRKCgYOLBW8iUAiq1Py1BEkiokDG6Tl4MtPnOvctw/TLbaiTQJ/G0lUCfFUoJozeHIKPHzchd/WAcX4JrshuJwsLcRW4SRG8FFCQAom5ydJ0t/3ilLth2W8EvASIrLoTQgczMoDER5ALDgD2gU/oJ7pN4zhKxe+BM8FoFbRWxFjPACrtKzZYg8M7irDO/VXumiKwCDs7JzJT0rG5GoVBkzsCfcrHKJjEuEi2QpfrgOrWkS0CTwx0jgrhVLKaIwl+OTxNbORrycHGXj0bMScTFaAn3dJRRWCl0SE7g0aw+ckLe/3SgnwqJASIHmMAZjBSFic28nGdgkENZOmoRfhrKhtVION8gKvyugcIpoxWB7cyPphO0s4GbtOnNJJKcIm0IZUdnYmsmj3ZrLtNFDpGVwPSnEcU+FXRAX8GQKxASRJFNYK85y7vJVcQbmEgCyndY0CWgSuPcSMJmBdjenuZKYIjY2tuLmZC/no+IkPDpe8Uj6dGwlwZ4ukplbKG8uWS/PzV0tiXHpUAYWUBhQFEZl8sjA9vLO2KESFpsie45f0CkSWBcDu0BJDGgv/Zr6yWVwWdJTyMwVicOxxvZrL4/3bSdRGZmSkpwBhQMFRXcnIlpWHTkDHou1tKrvL/6wnnKB4fy4dR9wnHRxRVSqPrCXC/GJKiXAHlEjrWkS0CRwbyVwV4olGkolu7BYAbUJaddk3cFTUlZUCFJbQwlFqDcKA//pWUvkm592YvDDpYHlIMBdrN3t5IvnRsrzDw+QT1dvl+Ub9uPuYJnAFRo9oJ186m8hzTavlTbp8dKmVSPZVWosWUmZcJ3KZHfYJekHfOXtCQ9LQVmxHIu8rLOAEAkqALay/sg5HKpIOjZtICamZopYFw03iCHr5sH+4g4FQ7DYy9mxMqR9b0WrHV2TwN9XArVWLHkFhRKVlAFXx12yQF77YcdhhIuNpVVIkHRp0kAuJqTKuPfmyfY9Z+HW2JCfD15+oYQ0DZSV/3lKHurcSp6ft1KWrILSMdHhKqP7t5OP/KAgPpsjGYfCpPB4pARkxEmnfp3lQHGFZAA/kZIK+eXEeUSGfGT6qCGwlGxlc/glqUDYWSxhhZSUyZ7TEZIBJu/9HVuIr4eL0Dpp0iBQ8sHmtYICcga4nIBolAtC1BqJ7u/b6bU7v/cSqJViYTJg5FXwUdxdxBJktl2nwyUP+Tv1XBxkcIcWEg8Oybj3F8q+fbAeQGBTqGpBgXTq0FiWTZsgbevXk9cX/ySffrdFp1SA4Aa2DZUPujQQ608+kVIvD3F7/mkxH9Bdrp06I/7Rl8V12DDZEA9XKrdAGTeboDzahQTI6F4dpGWAl6w7d1FKwegVC0t8XyHH8HcBWL6D2jUTfwC2cSnpsuXwaVhOIo2B/VxCaJr4kLOd7b2XrnYGTQJ/cQmsXv2jPPbYY+Lk5CxNmzYx2NUyxlvjlnItR3LhAhEIvQJ353D4FbG3MpM+bZqBm1IiU774XvbswSBG9rEKHxcXS4d2IbJs+kRp4ucpP+47Lm9/twkuDCwVEzMZP7ST/NStoTSytZTyFs3FpmULcZswUTwnPSMmLZpJQXCwDHW3l5X9m0rDUB/gLeVSnJItz33+nUTCMhoG62fVK2PFwRtEOLhLILDAQjKRj5Zvlznrd6j7KgKnhtGlKOQsXb4aL80D60k6okcFIOIZul28dEnyoUj/ii0y8qJ8NOsTSUxK+itennZNf4IEclFBYPXq1XLq1FnZu/eAhJ0/b7CrqLFiKUOGcjKo+sE+biDAlcJiMZVBnVpK97YtxAHWyby1W+WHjQd0bgldnLxcaRTiI/OmjJcgWDhXkGD4369/FMnIQ5hZZNx9neQt53LxmvmWZH7+hTgOHSp2PbrD6iiRgoJ8cXjwATFv3lxy3ntHOq9YKnNaeUuj5kFQEhVy8VyMvPPdRsA2pTKkQ0uZ9+/HROwBDgPHQZYiXK9SeWHRWtkG66Z9oyAJ9nYDec5NMjKy8LUpyi/YokwDrCADNdaQeeedmTJkMKJTLVrKmTNwA/9iLSLivPz39enSonkLmTPnSxARoXC19reQQAEmu+qed0JCooSHR4qLi6tKBH7yySelXj1/uXgR+GUdW40VSyZmeRPM/A6g4+86dlY27dwvfnYWEuTmJEcuRMubP/yCQQ+WmjF8DpDezF3sZdazI6Qlws4I3shcALnhZ+JAdLORx/q3krcts8R43kLJb9ZELLp3A8BrBsXSEx6LsZiDd+LUqZOY1/MTq/uHSAGURYtVK2Reez8JalIP5xFZ9uth2XhYN4BHdm8jb47ur3Ov4OaIJW4r+Zq8/c1Pcg05RF2bhUoiEiIPX7oq5xF69gTnJjunQAoRPTJEi4qKls2bf5H0jGuSAeX7r+eek6I6WkTsDGlpacjgTlW/c3IQIatDS0OEjArbCpG8NxAIfOjBRyQ29modjvj32ZUk0ALghJxA/j+2LzCRPPDAwzf1yStXriCBNwEogoXs2bsL1myqXL0aJ999963QkKhLq5FiYT5PHOqpuAH0TEJNlVKEes2RJEh3pwCuxgfLt0hOwjW4QBbSqXl9WA/mMhXcEuIcbCcvRMt8gLyKEAc3amgDP3HctkPSwGGx6ttLfJ5/QWz79IViQBUWY1SUQ4Gncigg+wGDxBffWYYES8G5KxJyKUI6BgcAT4HJA7fsy3U7JT1P53q88FAf6dOlqTh6OkjLRn44l6XsORopi37dL44ot+CJqBBB3OTMLBB5jcTSxgp5TbhmAzQHBwfJz88DtaZcHBwcJQoP7MMPP6r1kdmBExOT5OjRo/LSy69K8xatJCSkkTRo0FD69R8okRcQmr/Lph8UpojQ2SEHa/+BvfLpp5/KeZi/ySkpkpObI9nZOZKWmibscBdwLv4urKOCvMvLheLPkTNnzyqlqr/2uz1WXfZjDtw33yyWR0eMlLh4TIz3qHEiyc4GVngPWoPg+hIZeR78UuTm4RWPVJsrV6Lk11+3SCEUpikmbkZS8/PBXEf5EhPAFHWVuWlN7iOXsyfCtiH1vGXPmQg5B9p+qwYBEgiLYjOIcWv2wXKoQKayh4O8NmaonEc284heHVVOD4//HZSKUjwWcFfgvmSXG0sFwCJbVJEzBmZTXlokFXhfbmoFYi1zmWH44F8FBI1PpZydG1iOhbuH2AoUGrewspLtR87L9hNhIMm1VVnN/x09WE6cvyiOKJ0wPvJbkewimbthtzzUva10bOgvWflFyKyORV0YL3F3tpfLcSniV+IMSkyNxMBbqbbZAlNiZjavm3NaOSy3bxYvkgED+km7du2q3afqh+fPh8u1a5kAvQ/KksVL5Xz4GTxc1K1BtIvHLYJCDIcCmD79P7J61cqqu9b4vbe3l3iCz1MEWgA7jq29k2zaskUWLf5GWrVsJfWDggBFmahOd/zEccnKuqY64aRJk+S9me+inIXhwG5ac7zntLRUECztpD1kZGICa7dK+3XzFhn+6HBp16GDLF28WBo1alTl27q/vXz5shw8eEi8vb3xjNB/cB3VtYiISCiWRXLq9Gm5cvmK+Pli0jJwo1X6vw9mSXxCnEydigkFEIAhm4cn8ufMLTD0SlUJ2C5duuL5YoIF693Hrx4ggjTAA3bSNLQhZP6oPPXUU3U+fY1GVFJGNvJ03OQaso+T4BI1RTJgS4SXixAlWrQFuEoONC1wlZawJvq0aCRD2zdXWcsMxUSnZ8sP+07CgoCVgcEi+cXyv+3HJaQ38IjMDMn4eolk7TsgDoMHin3ffmLq6goOXTkylAslAzNFyZo1iPpcE+eHB8u+esGyeuV+ncvFrOecXFm/75QMBdZjjZB3tybB0hU8losJKeLlaS+JuN5LF+NlzZ7j8uLD/cAQtpPDp7LlCr7v6tpQ0kC2S0VSIxMb69J+/PFHOYvZ1cTEXJmQphgk2bi2Z595Rg6g85qTzFdN27Ztm0SER8hsRMSirlxWW3AA9+7dR+rXrw+sGjWAr+/n4eEpU6a8VM1RavbR4MGDZfDgobJ06TLxRWcqA5aVdS0L+tlWdu7cLTt3bFdytbZG+U97B3GC350YFytbf/1Vnv/3cwZTLPFxcTJ/wQL56qsFkpGeDt+/WA7sPyCdOnf63Y1kXZ+9jx87AQVw0OCK5ccf18irr76izvnZZ5/L5MnP3qTc+CUtuuMnTooFJkVjJqTdg/bR7I8gk/nI6jeRc2HnZeWKFbjfhgY7U6eOHUAlM5X/ffih+Hj7AEepJ0kA8TOR/sJJtWP7djJ61CgZOXKkOufhI0ckKDBQ3Nzunql+R8VCtzIDyqQeAFhzdHNH5ADZQdN5OtrKKbBtVx8LV5EYjoCQQJ/rtWjLlJtEu2Pv6UhJIlOWfBaS4aAQLodHyyQI8csR46TZiiVSjjCzEcyx7G1bxW3kaKkoLpJ8KBSb+oFS3raVGMMH3NW6qzzz3Q7JSATWQFeoAliOhbWsBnN3GjKrm/i6qQwAlqx0R+JhQ28PSYxKUfyWjYfOyBODu4kP8KAGgX7CeFAJAOgQvM+DNVDXlgMl0r59e4mOuSrJycnohOYYsNYwLQvlwIED0rNnj8pTpIFH8/PPG+TYsaPy7bfL1EyZlJQsDUJCZdSI4dKiRQts31OcnRHpMlCjib179x4M5AwoOXNYJixYjrIUyOnKuJYh/fr2waAylhTMnHFx8eo9B32btu3ku2XfKiVniEs5fPiwzHjjTdm9d4+4o+ofXWx//yBV5oKmd9USow7gHLFZAm/jNRF8pPVmqGZpiUqEMP+ZNbJ23ToZNWqkuGJSu7mVqXNbgRO1dOkSVWeoS9fON292l59ERUXJ/v0H8VwsxR4udTIG/CtTXpX1G366yyNWv1tQULC88/bbYg2IgX10x/btMunppyUmOkq+mjdXOkL5bNz4sxw9chRW7FL198oVy6s/WA0+vaNiyQVPBdw0RJCt5EpcsoRdjpVuLRsr7b31WJhIKnAKWiKwWALcHHWnRK4PBzjbwTMXdaFg5vdQ+/BJorNcwOcTS4tlOpTLwABPyXl3ppjDHC978CGqH8nfv0fM4e+XvPySrI7NlP8u3ylFSVk6paK2wEaoKleE85+NSoBigXLSnVJs0Wnqe7vLbpyK59px9rKcvhQrbRoGiDdSEIqguJLSMxG7d5D4lHRl8teGMEdTfufOnbJr104ZOHCwPP30JBk+/BE5fPgIUPbzCiM5dvwkBu019bBcUBfm6NFjkpmZKT9v2IjIlBkUSHNZ8PXX8s2iRdK/v6eMGj1aDXBcca3bvn37ZNmyZWoQ0rS3gNnLVoF/HKznzoXJhp83KlDc1c0dSeMI20MGTBZ9c8YbMnAArEV7e+AqkcKQOd2wbMxmvfv0kgbBwbW+nup2+GbxYpn90UcSHR2jmAicOcc/OUFat2ojffv1vWkXRtbMcB9WsKAWQUYPPjgMPAsdZnfTxnfxAfEkCzwHMzMLPLfDsOAQUbyhcVKNBMObM7eNrb189/1yWb/+Zxk6dIh88cXnmDzqnh6yddt2hHnD8ewsVD+ke3LqzPEbruTu/ty4caPip3SGNUiuyrbtW8Ud90LXfeCgQTJhwgR5/4P3Zf5XX8n33y2T75evUEqefcPRod/dnfT6XndULFyWg/VkrVH8Og/gohUsFid7OzXb7DwVed0ywYiGRWJL94Tt+gjPBbclArR6eETXG0c6G7aHCR4VFitP5ZTKxlE9pB1MzYyduyR66lQxJSJ94pRYNgmR2bvPyMfbQbjDsVR+EJVTZcN7XNOZCzEysltrdVh+xep1Vpac3XAezEoCAt2x85elV7OGGFDFchzUfge4HE6o9ZKBCnWsJ8OVAmraCGw+9tgY5ZuuXbdBFkFB8OENGjRQveLjE2TsuCckIjJStm7bCaD0kJw4fkzhG6NGjZb3338f5qgfQnwpcu7MGXnppRcB+jrU9PQ3bcd7WrFyJUp6Iv0BzRgYSjlcnRubbz2UCMUjonWQC3C0e7eu8jQwFH3rADyDL0M3ul/Tp0/HwCnDQDaRJk1ayGeffSatWrWqPBWv6VO4JKkpyYiEpckOuGeu7p7A840ALqfC8jsE9yBUmfSVO9XhTVRUjEwYP1569Owlz8BlfRd0gZFwB3IQRTxx4hispFhEzeLl+PHjyi1MSUpEjSALBc4TqzBUS4H1UFRYqPLu9ICpr49fnQ9fiGPOmTMHE10EeCon0S8HwEr/zTqnez58+HBZtepHWbJkKRyAMvH2RSIvLPgpU56Tt958s07XcEfFchYPgHVmTQTlHsEBCUSSnzfcoERwQs7CFYLpolMcyAnKBwDLpjdrc1DJjaQ6ZamobzDQlWLAdqy/ArNMQHSbtvWULBrzlPgsmitJc74XW3zn/Ggv+bFNV/l4K1wtgrfQ5LpWVbHgeLCAYlDnpQQd0+y6QuP580HxVxnQzJiGtXQA7lcR3Kd2oQ2kc+tm4oioVhlC0w4wcfMATtdGseTivq5hRvf08sYgSIbmHy8LF34tXbroTGQPlNBMTGRSppkkJCZKanKCNAwJgQJ5SSZNnKhuY+68ebJ8+Q/SAsBpXZQKD9a7d290hLdVJKqouBAzvTkUS5m4OLtICAbjkCEDAJSm4xoXCa+d/jYHCVdv+mrBQuW6Dbt/mJo00uEC0eoxVCPgvOann1SHpXuYmZmsLKSqSoXnYmj9P//5j5oLWJ7UysoOYVBLBfBOnjxZ1q5dq1zKhg0b1vnS6I6eAG7So0cXtW4VrbUly76TTZs3SwGKjVF+Dz34IBRgqOQiWnYOOMvAAf3kiSefAEbhK7yGW+Fmtb04KhU2Y2MORd34scP1cLKg23q3jWA4X/EJ8fL9DyuAOXpBpjbKrcsD9mgD2gjv0xnWtGO6ozg4Oqkk3XQYEm3btKnTuXnNd1QsSfDLfVAFLikrT7YcOgVDww7LdphLGDR6wrVcdAQMblsIADjMNWQks+l95SIM7kIOcFoOSmi0aCg8KgcqF/wyN5EzwGGeMjeWBRMmS/MunaQYQv7WzEYmbwMTMDNfbaMXOvb4rXF/KJEiKAj663pLiedMZCjZAhvQcsElRMQm4voKEA1yQK5TGvDmXAkAbmQPARdzzZFaNKL277z9przz7jvi4ekNDCkNne5J2bplM8J1gcoySYU1YmaOKBfKab708hTMjk9KaGiobN+xQ9atW4/XBlTVS0U4t0Ref2MGFMOMWlzBzZs++cQ46dWrJ5B/Wiqomcdbx8AkVdvd3U1ZT1/OnadKf7LDMjOds/HBgwfU4/hk9sewZkwUYGwHi3TSpKdhlY2++UQ1/IS8GyqEDXD9yqDQnZ1cxdLCClZigfy6dRtWZ8iXiVCygYEB6ogc3C+9+KK8995MycspkUKrIvHy8ZXcbJAy6wfKsPvvU4pz5sx3agQq7tq5R9ZtWCedOnZSg6hx41A1iIoh7+XLl4NzlA6Ft065NlzjilGhqKgrsvyHH/CcGiPMH6KwJj6r0XAjqLwH9B+grtVQPwhKr1y1SuEexL3YhemGXrp0Wbp16yZ+fn5q8HvDbRyG1BZamDVtxKPc3d1xXw4yG8+WByeGkwfi6iy4pG+8/l9YzMkSezVWhbmpyByhXBhBev+999A/1wED/Lamp7tpuzsqllzQ713dPFSxaib2BSJ72Rb+YOTVZFgSGLHoNJMGdpQ9p8LkRES0lIANZwbzlc0EN0c/VqdB+AmVCr+DtaJwErznCICiOnXsvDwGy6R34/oIbefI6oMXRdLBUbHgJeq3x9vKhv34JNCscA5TWk7XGyn74QgrjxjaDYui5cmOPWfkYnoWCkHl4XAmchQ1W5jl7O/urCyPwkIz1Me9jg/pD3Kb3zbw+zmb0WwsRad0dHKSy8Am9CQ2hnM7du4i4WHn4AK8qdB2+uPkQ5w7dw7ux0QZgbDeI48MVyS15T8sx4w4QLlTtzntbb+yBZv4drkem3/ZDPA2Ddfqonx5Kn+WEC0D3kIxRoMYBXWkVL4V3N4ZM2aoRzMa2E9t25Kl38I9XCBnw8JhmBrLA0PuU0p41qyP5GvgJVdiYuTwkcOyGKHurVu3QpYgScKCoqXn5u6hlBpBzc1bflXPmNgV3ZU33ngDwPcmeeKJsbg29qNbN4aIV6/5UX76aS1I4DkKV6AFSeKXsgawMB7dhWJEpWh9ZAMPW4PoHgdw1cb6zIVYTaIu1kPV4zF8zQjiGkSljp88DgsuC/iNHZ6Bri8T62NO3uUrMRIVHatWCqWFuQyDnC73EOA7NW2cWIgnkp9C/gr7oAWU+0YED2g55+XmShIsmkWLvpHIixcV1mIPLCke1QuuQP7ElB4brYsU1fSc+u3uqFjKgD+QBp8K/z0dA751kI9SDSyXAC0CXVEhnds2l9Fdm8oPm/eASJclge4cpNCQAHxZ1Po3xULh6QSovwD1txEuA0bPhRMX8ILrUw7LBmazsjjUhtyHHYmvG5QTOpibvZVSPbpjGyGcnCxdQgNlxpMPyAc/bMYuZVJWUCJpwFOCvVylV5umKgrFAt3k6ND3r23jA3J2cVGgH3kmXbp2xWzsog5TAWWbDtejZ68+MnbsWGWSfvHFF/B1T2HWnQUrQqfECNgVITp1DZwCgmjrMUsYqjFSRTfsGEDjH4Duh0GhOTo6K1BdWXc4ETuxsTHcJtw/LRtHkAjJX3GC8omOicUAn6Qsm2HD7q/RZXFwsMMeOYYBg1C2jQ25IRUSAUzqgQceRA3kOCUzYktTXnoZHT5Xln33vbw2baqyGCIiLmKOMZdp4HLs2btXfoL7w3bq1Gkh/Xz8hPHAQt6FC9NdgoIC1Xe3+pFfwMFUodjVZoi45BUUI76gw52MTIzVc7PGQKOrkwmFu3r1KvCObrZISqB8LaGEft64Sfr27Sc+Pt63OuUtP+egfuaZyXLo0EG4yInquoxhmRDEt4VS4fPg8sFUcgwMUNMbY3I2R2ItqGpShD5K5VBSS2zH1dUNxzVRyt0ck3wpAGoqqQSk1/wKhe4Bi8bR2VXWA9gnPmcBOfEc3j5eMgFu3/33Db3lPd3pi9+m+VtsaQqh8KZpfbgiRGhhrSNK8cFBQtgLLFm874Gw8AvjRwGz0PuFoP8jLNwcyYeqnKQa+jyd/qVXEjwxHjhqsgwe1Ek+njZexg7vBfwF26nxzu31Con70J3i39iHWh6dpHVIgFI5AreDgzoQS4rM+ucoqe/uBEFhO+I52DSHLF3cS2xiqvy867BEJ2eg89uIEQHeWjYOygp03LSUJGV9rP1pTWWniwRoy5UG/jN9mlIqm4DOL4K1wpDfY4+PkWbNWquOht0RUDNVDz4bysVQjQze7kiTaANf+dlnn5XTp89ADuTE4B8UMWdgzuJ6hcrOlw2F0qNnTzkLMLkDeA0ZIK9Rbmfwt342vdX10QpgyJZkwPWwKDIwC5PzoVNckHdsrGIUW8LHN8WAcgRPZjnA5j1798n9cHHI3VGWRAln1zxp3KSZkg9nWBc3T7gtmQBwk2X4I4+otah6wuX75JNPQbPH87xFW4Yw+dhxY8QBE1sO3CnaYsawVs2hzDmTsz8wOmKCaCbDrNUpFR7aw9NDghs0kHVr18nUadPAvo2/ozyqXtJ2hHVbtWqtwPVE0Ao4YtgH9Yvr0aJjo2XB8iNj0T8eeuhBZX0GBtRT3gGt/sfhlvbujXFRixZUv75S2BwnfNYcLnR3qdSmvPKKTIS7S9nv378frO6LSi75cJUefughefzxxwG0myoZ08KrbbujxVKMqzFFZywHjhGDWcwTD6o+yHKcDfQtA7MzWwhCvDqLgt9RCYgM64okwc3w44vUXanPdD/0lgc+RwTDHvu+OuY+yQUZrl+zIKnn6STvLNyEw3A/Ho+N+1Cx8GHg+Eg69A/2lC7NdaxMgpHcJhB8FX5fiBmAILOSKD5hSJGDJD49U7IxA/CZltOkpsRr2TgQ2Cmo4clTcYH1wsbZ6bnnnlemekBAgOIlzJ0/HzkYV2XOl3PVoOaAWw2zm+AkzXN28oSEBBUlol9cl0bz/r33PkDeRwp4Ge48dGWj+1BYkAeXwwOFtFqA27IX1w8cDGzcZk0by8cfzVLbDhjQXxYsXIAZ1RIEuR0yevRj4LIEVh6n6hue78svv1SWmCMsNs6yvD/KtADnonvFyI4NXDUqPDYTDHBGf76a9yUwkI66z9DhZ334P+BO6XIBijkhIUlFYHiMrOws5TKuWL4Cx8QARBiaFt7+Awdl1crquRZ0rebPnassAGIqO4G55MFCSgdmePzECbXkjAu4QnQRmzVrqq6huh/t2raV5/45WV55dRrwog2ShQjjkqXfVFqd1e2j/4x9hOB1ROQFcXN1ES8wn+lS1fMPEDu4HBlZmRKJqA2tu6LCfNmwbou0bo3opoFaxw7txQbBCeawkeHMyA8JiexzNlDyelzSEgzvMvRjLpvD734BI3vnrt1qWwsoF//AIHly3DhEDNvrnm0Nrk+nLm+zIfNqqES83Gj2YyF3DHw2a2p9fMcBfOxyglrQnRYDNlbf635USF9UzB+DtYSwbiq+o/VQ9XtuhWOgDm721XT5atUW6RgSJE3QiccN7AoLAAoCglDOvjoghwnOwdOSXAOlM2V4n+uKhOMTuUZ0q66PJi4RcikhDdvhNrGtNWarYtwDQ+hWiArZAkswwvG5CH1tG03oDACAHERViVsrV66CO5Epb739lkLgN4O5umXLVgTA7NSAounLlzUsJQJ1bOxsJXArd+7apf6+mx8ckCfhar3++puKgs4B/lvTCYRKNR0kuAlPjgMg+bgCepk63xR40RdfzKncnNdDCjg5JOcjw1SEq/LLG95sQSecNm06cDh3ld/DrxnWZIpCSINgefHFF6DAdsuH//sAXUM3aybBJSIruWfPnupoVE68Ns6eJGtlAu+wBK2hDNvzPmjSk2o+++OPFehbBIsrF9ZnfNxVtf/tflDBjMOgWLz4ayihFTJ0yGCAyJaSj0H0r+cm31ap6I9LkHnWrA/B+3GQjZt+hpxPqglE//2tfjMq0xGKswesR7JZmQNG6+CH778D83iu9MGEdBHYRmZmOvhQQwxGRNRfDwFoTlSFRQVqCD3zzNNSfN3VatO6HTLxm0GBx8NtT4MrlK4Ik97enip1gSkXYefOy4mTZ+T777+Xf/xjsgL79ce+0+87KpZSDNYSzPQ2cGsGdWotnUOD1TFV7ViYbhzkO85dkgsIGzO7uRwmMIMsdEnQjUAjMZO3xj+IYk+hKNaE0DMGtVIMuh+668M+uGMU504BwARXCjOrPxRZzxYNoUcwy7G8pdoe2/EtTeCSfJk0oreM699FdwxuQeuD7frvfWcvSBSOSSIdpihxAuZTilmTbooH8onsAMwVoPo/Cz/VtlGh8Hz0WUnmYksCJ2HGjDdV6nmP7t2VlfLKq68q/EKv7djZOJPR6tJfL12UnJxsOQKi1t00Dsr3Zr4Ppmxb+RKzNGx+pfB4jfyOE4OyVjCAfX0DQIS7LC8gApONKF5IwyD5CSznJk0aV56a6QTNm7dQXBe6aEcwIG7F3SA2YwmXJR4WhhUGbE5OlngChB01agTYnTvk1VemIIkyWOohwsF1pigHC8yQjRs3UtdIouG+ffuvy0QQEUFNG7hDuuuGVQgZ0wq0RhSLVPTmTZtIzx7dZAh4GQ89/HDlNdfkDcl5icAX3n37HVCiEG6vYnXfaf8J458ExtJLua50iZgkWZP2yScfq2Q/H+Rq3dh4X5yU8jEuGoU0UG75jdvU9W8FOkOGZC6ToEnmLZX+jt3bVHTOF4qkJzCr9xAJokU2cuQoRaBMB+7EYITuWRjJyZMncR+/1vhy7ugK2ZgZg0RWgDV8OODRUdUsW4HlNGBN4DsaECkJmbJs816ZiXq0auTDKmEyIS0I/mRYd9FrT8v0eStkzc4jKjkQMWtsypkULwCYOIE82rudqkxHpWSGQfH0fd1lLaq/5V2FlQRrAz0MCgVJdG4OMnXEAJk2ajAUHhINrlscOleIg8hYsgHWLdlyEHVhCmESWCptTHAyBWZnGQaeBR4o83ksiJSDtFXbxk5B7MkRA4uclIDAQPll0yb1ABcvWaIOR5CSYed6IKbR7MR/zB5Y6RHnvq4C1XYcPFTCMYiW3E3bC6Bz29ZfEYZFxA6zPjtEanKSuCN0yNwfXisHKjELKrFvwbLke+Igbdq2V/tUPa+Xl5cE+teTI5hlzWAa/wqL63GEXKtz08hHmYacm9kff6Jczf5ID/j3v59XnB4ClTwGz70PM7Wjow601kVi8DzRXoGvP/2115SCPn36rMxEuNnDw1uFQPU4TWpqirRv2wbJceNB6npU4VZVr7cm7zmwtvy6GbK5JoPA65n+n9cqlVlN9uc2HKSUG/O71Mxf0x2r2Y64BZWobpIRxY3hJFXXRlmzP+kbj2mEcUhsZfbsT1QgxhsUieYtmkhjhNUJqlNhhyGC+TGe4XK4m9mY5BiltIP7SmuSk0pgYH0wxAfoD3vH33e8Eyc7G2i6a1KC/JsjF6IkFIu4eyOZjwCpCayYskK4Kui4n/68Rwa0bSI9kITIiaACSoPDlRgGb7YRFhJbOm2iDO/RRn46cE6OXwCgB6KOG3gmbQM95UFkQz/au4MacLr8ZpFuKM696IUx8i7KMkQCaA3Aefu1aCAPY7ue+I6NawdRPRlX4Dw4J+wA9fk8rGO0bf9ppVQYEg90tEGEygYV3gBUIlPbxs1ZXEHvz4LSdEBZhdo2ErrKIXAbgNnMO6EvHRsbBcD2v+BsOKqHsWPHDuhDC4T4LBWOwXMQREzHQKG7wIft7OKmOiyxiLvtWMz7IIkrAA+fYVMnhNIffvB+cGZ2qY5BsI7PgC828hXY+SwsKmQTlCGv923kkegbiWzuSHokJkU3JB3JoiRVVdfY+abAKiHAScbxs8+itCgG4FZwVeZ8OQfA6Eal6DlTuuBYxFl4bv29cmDlAfw/dfK0PP/CCyoUa2vvWPk9Z1lXZyeZP39enVwFRlXef/9/MhUWZBKslmuQE63Hqo3XEhcXpwBLKkFPTwQeqjS968qITV0bLYBVq39UoeYCYD9+fr51PaTa/8TxE7Jn31554fnnEa6OUsmG7H8FcP2cIcd/4Pl07NQZYHV/hLzX4vlvhAu+R3bv2qH2p9Xbv18/mTlzZuX13KisKr+4zZs7KhZWuC+BduXaQU72NnIxKkoaeThKIx9PaQxlcfZaEgYvmK6JKHA0Z6Use22CNEdtWfRiKBV25OsaFP6yDTgpI6FAhnVrBwJbBtyQIpik5uLl6oBSLpzBYJEo85QmPNQL9n+0R3vp1CwEfnc2yuhaY2UAl+uqg9oLg+W6dsYCSQgn6jy7pTsOydTFG9ThVBoAgLH2jQIVgzgO5LhEKAVvWFGFCCWmwWrxRAmF2jTmmRw5elwpBloixCnoygwcOEjeeutNdSg+DB8fH9VJL6AWBuUwBqHn++67DzVPUuGSlSgrZsHCRcpyYldVoGdtLoRHhRjSMzIwSHT3npaWrMh7T8OffuKJ8fIDCF+eIFgxd0uBddhHP6PxdykG0/8QAucgmzFjhjo7j8X8JipkujmxAO1JrLpV476PPPKbW3IOvvkLLzwP9ygRoUs/dV5GnjhwOSidAfJ+j+tiVCg5JV3mzp0PhVMm0RgIDgyJwxIkvsLfmekp8vTE8ZgxA291+hp9PmXKK8h3s1E5XcytKgZDWa8o9AdgDZ1NwFBoGJNo2B5gpb+/P8Ku9+M+vOUqlA4tjQYNgqH4ah9J1J+Hv08AQGZdlKD6DZBlngF3+hzuPwaTU4w6B3E4JkbeqNyqHqO69+TITEESY3RUNHhE5+Ti5StqEuvWpYtKOhw5cgT6TAWwrymwUHRgPScPunfMxWqICaJdO2CiVZq+v1T56I5v76hY7AGiReXkiS86oQ1wihQAKPGovtYWoFC7hgFy9nwsToLD2FjI2ePnZdj0z+Tz50bLwA4tYLFAOVC3YNSUXR/04HZiTTLUbsF6zFUb667oNSNvhIlytECM0Ln9wJblq7JxNKHhWxUwqgBoXIHjZ0FRzV+/U6Z+9SNiy3CBHJAyQK4NjtO5SZDaJ+xyDPYzRqazMwZ3GdIUiJBXBTrVZrf9wYdP98MWYB6vmS+SrN54Y0blfvSdmYaenJwix44fAxXcW4GILVu2rNyGb/YCX9iH7Fbq02tw02rbmLd05MgxdHSwaaF4W7duKyPQeRZCYR06dEilC1CeOondfHQmLLq6ukABLZdGjRvLSICkbJzdWPyHWdCZsFhqE3L86KOPJBWRQgcHuMs4N3lElBEbFSjxElpT5GzQVduPGdYUrrGPbz1F4c/JzsS5zSUouIHahwzUu1G6amf8+OSTzwBofwO5PKqyxukqVtfIjKZydQEQzbIBnyOfCScWllhgtjmTOa2gnOzx3OtyPawFw0gayYBUoC4goDKdgW5HMRQaXXnigKxKOGLECBk/fnx1l1vtZ26ghJSVFsNanAcFiaipf4D8C1GtQYMGg1HcSO1DBcaopC1YuRbg6PjV84X7+m9gYx7VHvNuPtRNc7fZ0xbhKpblY5FoPw83iYxNlogrceioFTKwfRNYK+gqAEAbBfnJh688jkr8PvLT7iMoqHSVwx4vdmp2qutuClwWmhL8TIc76Aam2hadmAmE6IVqBlURHv6tGo+hUz7srFAXUFa6lxGyqUHTkN0o+nTgzAWZOKSH/OfZR8SYCgOlC3z9oQQb1VfU/xxQupkC7wE3KB+mPmv3EiupTaN5ztmVURPcHgAupAq4e0k939+bs4xIvAI3YQGyR2fNmiU3KhWec+iQIZIDDglxEB63tu3YsaNwg8LgZrgjShKL2rtMjbdWSX9JKakKA+Ix9QO76vGpcFjwh0qRLuVMkM8OHQIGhqYwGFO6UMCSYJHUZtaKjr4CAB9PFPuxxghJb/qmniLulWRCK0QWQ0MaqYhUUFAQQvModo5IBfOCXpnyMiw7sLvRiopqz6PQn4/yITX9qQmTQGN/XX2slwU5TFXbsGH3QWkgRwl94uPZsxRrNwAD8+CB/So0T+uO+1y6dEHJrOq+NX3P57xk6RIJj4hQblAxJsMyRCaHoB+0QL1kyorZ1FQ6B/EsCLLPA4ZXk0aFuWPnLhWB9EIpEhbw+uzTT2GdvFipVHgchr3Hjn1ccYLYVxJgia0BgH8rgL4m575xmzuOKDNYF1xNkFR91jlpGuitQrWMa3RGUaXQQKDdGNURCOuGAH9Z9dZzMuu5JyS0nidVhy6gg06mvFl0UhXgua5wqHLo7qgGZUJ3RvfQdZ/pOwCVEq0TxAiubwolhbe8eH5SAbAYXV/6tW0qy2dMlq9eeBwzOIDjdIC+4Mj0bhWK+iyuEhETjyhRsni4OIkjIkSxcUkKA6nNoOEFEB8wwwzLWZzXoAYfLuhWD4ZJhjf68zwOG+tgEFikmX41Lh4s2ZW6L2r4ky4YOQg07Zm8xnIMpMmT+EUCGPNBaBlUd4809wMC/JVSI7+BHA9iEKzoT4ulAlYjZ1TeX21aQADypYAb5IB/cvlSJMKXF7G7jl3K41Dh5OK7bl06Y9DMUaHM9evXIiqxXqXvk1bfFyAwKedsV5HPogc51Qe1+PHqq1OVq/rOO2+qEp/cVd+vVMTkhmONHj0KIHqUzAP3iAPcGniTE9ip5P4o1wldk67DokVLbtjzzn9SxvOhJJja4e1TT11HcnKi/GPyM/L5558COP1eFi74CnhIDpIh8/EMsMwOLIqpr01HyPe7O54gLCwM17VYMazjoSwmTXoKrveQm/YjQfAZlPoYOnigIkqW4DnPgjscHh5+07Z3+8EdFQsPbIOIDKvac6EvP4QSmcyXgHwCH7gnj6CQtZhBzaRmyXsrt0pKVo44AZC1QOfhLIiakzgCLRSqAKiS6yYxj6vr7LBpMHPe+Prte77TKRBVCxfbqkPhM9pD5M5xXy73TG1vhUG/69wFmb1mu6rWLy62MhKAMX18LgPr7+YkzWFdkZNTDleI91TbRhLT1Fdfhv+fjvuiiqX1xbjOdSVZiwOyRi5viWS5a7Bc6BbUpsWhAxEDMwNWpQYsQLquSC9g5zGG0ib/oFvXbjeBr1QWxIZY7qFH954ww4F3AYy/gNn4ITBcZ7z1LgBcDiaGxWvUTSov+yMQ7bZv3yb2AHaJn/y4erWa4YtwnXwOpKiXQan941//BI+kmdovBBnDffv0UWFpfkBlSGVsamohERGRKvu58gQ1fHMcQGYUsIauXbvAonOp3EvX735TMJVf4A0z0JmQyPV2yJ1h5JAuHa0VPii6QHmwUD/+eLZMefmVqrve8X00cn/eefdd9BWC1xUA2tORRf0A6vk8rc7p6+sjj+KcrCxYDmuaOBeTRemOvvLqawj3br3tOfg8c3OzYYnkyMPAvB4Cg/ZWzRfW9TPP/gOTEdI4wGFp374DqAi/t7hvtW9NPq9Rj2FVe0yAEG65uNlaQ2lAEcDMp5IY2aeTeME6oQlx8HCYvPfteuVyMDRdztq2nNPxQGid8Dcr/eubTsf89rf+c2z421v1Tvc3rRRmCxNU5Ll1M6nuNwcRraIrqZkybcGPkh2PqnVog9uFSi9YLFdTM1Bf20Ia+cF/BgCcjRC3E7kstcRXeEy6OC9PmYLs3WdgvsMiAAhMRnBtByCPRauCionWDsOrLHdQm0YmKolntKKSEd4lzZ0WEjOXT4Mw99SECYpIRgumauO1stMSP/BEeJmdtxT4GTOyMwAGs8oYSXJU5rVtxCNYUe/YsWNq1iQIiocF1m8B7hQrW4JY+Npr02CV9L3lodlNKBNS8MnUpVVX2/bZ518oxUBLgcfii8c5e/acOhQH4o2NofZ//vOfCuMg7kVlzVIUGWkpkpwQp1IfWG6DeWY7UOhr3LgnamRN0c1lgSiuQkFlzRy8AkTaBg0adBNAS5d5z57d6lmy+p8DIlR8HlFRl2+83N/9TeInx05uTr6K7NDiul0jq5jZ34z+DRzYH3wrYGIGajVSLPST7REuTMnOVWsj90H1/TBgKPtPhkljVG771wPoIFz2AwPjk1Xb5b+L16nq/WYQIEPHVCA6i0W9UZdOxVD7Lstdecm/vYjNGGNQ8HcUrKZ/fbxUDh0NgxUF1B58l+cf7q+4MQfPYNVGzD6soGUPvzIFNGcPhK+NqK3uojGB7ePZsxUoGFw/SFkH+pmwNofjDOiqOgCSzTBLs25KbZoZFAobz013gW4RGzsJX2R2nj9/Vpn06osqP1hCk9XC+mGAM9eJRC3iTVQotIDUg6uyfW3fMtmS98fo2GeffgJw2QLnwNpOcFMbIPpwO2yLCYyUSz5mYIa0aUXUtl2+dFHxNhgZ4WysCHYg/m1BKNwIXCYqnBsbFfQ0REieffYZRCGtcM3m4oukPGZlMzN90kQkZuI+ChBpTEVk86e16+Bu3K+svxuPpf+bz/Whh4crTIXRJLo5oaFNANofkCeffFK/2e9+s3j44m8WwZIEWRATB+W4adMvKu3jdxtW+YNJhS5wYSc+NaGyfm2Vr3/3lkAxrbmrsVdhqVrJhx/NViknB5AmkYgJipMLt7nbpuuVNdjbE27PpSSUioRCiIm5KruPnZYgfz9pA2Bt4tDusuHASVgskYo38uGSn1U9lNfHDpUGKn8IJ1DahdYFOj79Fg4EvKgiajq0FdgLy4ScFV1qAPfky1h2gmX76oLVcvTYBVwDQFU8zJcf7iV9sLh8JKrYZSCyRbwI07skAHthp2hS72Y2JA5W48aHPeLR4erFnaq6eTU9SFVlxHA5K7sR4OOxa9IIROsbK/ATJ6naGN4lB8XMAi+Akhw4bLxW5pE0A5OVCYvME2Gd2WIkArIoOLbAP/1P7lG3xpmZhDBWK+MAJ4/ido3XzcggmxNcEYLJtW0cIFnIyE9G9TeGb2k1sMhTeUUa7h+F3qOjq5U1AVryeqpye/Tn/uCDmYqB+y5cmrDzEQoH2QH2MMs6sDxode3EiZOqWDoBbXJW+vTuBWxpPhS/jjBY3T78LDg4WO4bOkxhL/UbNgIn6BewlPfd0sVh0ilD2MT+qpMXnznrKzOquWDhQpWcyr5BzhJ/v4y6Qcwlo3tOEueDKHb17jtvV0uMvNU16z+vsWJhhTWGm+OT06Vl44aShGr7R89eVBXxm6MQ1PuTHpEHoj+XzBQApgA2l63ZJUcvRcu/H+gtD3ZtjeLb5IoQS/ltwNBRqqlS4QXr6+hSKekUSgXqwiSAYXtAPt+0X3ITMKiALWCaky4dm8rLIwZC25djkbJoLP1RIL2xFGxwPW+5GIMauQG+KmLB4xqqVVUSNT0mO4DuboxUZ2BnP3HyBKp4/Z5LcKvjtWvXDh1Qt+IAZUvmbdXGfJH69YMUD4gWAgcUfxfDnCefZjfMebp2VDivI2oyETMygUVjWJuUN7EFgrx3ozSrXgffEyydOPGpGz+u9m8mGuqjNqx0djey7dGjBywTYwWMEoRlzsvevfuwps5lRfpkcSnlbuH4tWn9QCDr2bOXcm2OHDsB5eKq1mEi6Y1M5BtbkyaNETnMB0myWBo3ClFyvpNS4TEIoL/44r9R3vRXZeVwZYU7PQfiUtUpFR6P0V1aV2Fh55Q1QtIiLSg+F2v0G1ZEpAXMgACjwN8uXark882ir7l7rVqNFQsfrCcWf794NVHcEapl+PYaZoPzUbHiDAC0O5bemDX5EZnw/hKEn2GVgNcSGRYnky8vl6827JJB7ZtJr9aNUOQaGZ64eeoG1tJlaO+3xjmSw4wzpf6d7j0Xn7+GeioUXDaS0E6iOPYWYDq7EGKOjknVbUy8BFR+/2Bfmf2v0eKB45MtnA/YonXD+lhPyEOSYOKRUu7pWjdrRV1kLX5ERl7AwyxQVfir7kaqO4FkU3MTsYa5H48q7Tt27KyxYhk8eDDyTEIVFTsJJRwCAwOrHl65AEwUPH3mrMSgzOgirHcUH5+IbNoCVVBZTxKjXEne64Ao1eFDR0Gq81auFa0gnWtUN0LY7y6qBn9wWQpV3gDbsgJe1UTPGuyuNvn8888qlRM/YNEqfeEqWmgcgDW1DG88J60/1hke8/jj8jNclPS0VFVMvTrFQgXG+rLM0aGMmzRpcuPhbvl3Y3CLnkASJQFx/4BACQgIuOW2d/qCbm9sTDQsFG8VRWRSLgF65j0xzw3hVZQuGYQ8sWYKLCcWNnr0mDsdttrvq47qajeo+qENrAF7JJCFxSZIM1gpjiCgMefGDYxcRn/G9OsqCWk58t/5CJmSZEIzHYDgqbNRcup8jLwHaj6LG4V6u8jYvh1l/KDuOsVCMFYpFPw0Isyr47hwxiR4yHByEvgW81ZvkZ9Rae5KciYiIdAWVGBstFLY4BNa+7rI3JfHSfsG/vgAWdkIkV+KAVEITGELaOML0XHSEC7c3cyA6hx38SMqKkrV+6C18ByISCTLceZhRGf+goWYVYkRMYTOsgaFqDx3Xs0o+oF1p1MG1Q+U16ZPrXYz3iddD77YzoWdlfknvhI3dKhEMGOrEt9Y+uHnDRsUp+LAgQOYwXwUaPnPfz2nGKjVnuAefciiQyyryFb1GmtzOr3FU90+t/uuuu2r+8wRIPmKFStUbhQjcePGja1uM/XZnDlf3PK7233B6yQOQ6U0ZswYVWPndtvf7jtapaZQiLkIZ5OW8CFKTzRqFIqyDhEKOzpx4riKbk6Z8tLtDlOj72qlWHhEf2AmBVAsTEzs364JMBcj3WyCaA1LUr46cgDMaCOZjkXZVQKgBXANDnwMqpYBXojQNAa9PgA5RQ3FHhEmBmlpvpBhi59qwPE3w8hsZNUCQ5cADxd5Ymhv8ff1lmORsFZOhQOlh9vFaAfxGyga93ruMu/FsTKodWOeDlCOkfhgvyGd26pQ6nGs2+yIOhRcxP6Pbn4AD/ft3wfCHFYhINaDxgQ/SwDJ9nb2eKBQLDDbmddx7PgJVV29VauWBr9MUsTZwVgP5MCBfQAdc8DXcK08D3NkVq1ahTV0lqKoto9iA48bN0YtD1K50R/whkQ11ktho3VBRfxHTgY1vUUSzBaAe3IvG12phcBE6tooQ2IprBHt5OhQqaTagD7Bc7DsBmkCXMystqkEN15brRULQ2VcvIy5PnYWTuIA37UQodtdWHo1AMzcRn4e8tpIhNCc7eT5r9ZIThzqoQB1Ri1IrHhaBoautwzo1FycroNxnKtVQ2QHqqCeJc8CAAAH6klEQVTy+qBr0PBDfQ4+A/5qihwkgpUJKN6UDpdHRYdKsU9pobRpESyf/vtx6YKcILZ4mHEnLsZKVyRFOoMMl5iSJjYYuE2CfNX3f+SPwMBABew9O3ky1nbZrlinVCBsxAAYuqSyIR5EjIRmKWnf90Kx0GpiiUIS33z9dMWab5QFq/RPnVq9BXTjtvfqbxYmcmBKBhqLPmWgipzKX7pXJ/wbHJfuZMcOHeQAUj32IyLFcpwdO5KkOB+TzAFYsZ4K6CbuVNdWa8XCE9phoOYVWctRZCi3CkE1LPjnzAcxN6lAWYJsKYYVM75/Vwnx9ZJXFq6RA0cjoDNKUaA3VZ7+4Dv5fM02GdAGdTXAL2kMJq8v0HFzNYtfVzLqrugQGUkOAK+rKaj6dTFGdhwPk1+w8mJKEiwVRpagUFC7T57s11OmPz5UVba7cJWVx2yRaYACTMiZYCmWLBQFykT5hCY415/V/MFw/Rog2ApUM2PV9FjQ3vmgqVjoAnGdZoawWXSHOSuGWOaiunt94IEHVNiSVHeCvbXlzVR3zHvxGV0AD9Da2XLAN2ESpKZY6iZpgvTvvjcTpLyHgbPFYfWIV0G/cEWw4BS4Mg6KDc38NlqtdW1g0dOPuLsWBysgFqHbpkH+Yg/WZiaIOSv3HEXR7Sy5v0trWBi+qrr/15v2yOc/7QCmAOsF0AjsfvzAG0drLCTvKvU9nMQbSYEeDnYqBMrAEVdgvJqSKVcQ4o5JSZekFCTo5UKRYCBCoyj3qm3TQNRkGSj3d2qlqvRvPHJG1u49Lq0a+gPD6YwKcagUBsUUm5yG9ZmdsXD8dSzm7m7XYHsxekDiE60/GmmpUCZfL1qM6unrQZgagjKPHyjlcrfA4p0udMXKVUiZX42oxlDF9GSH+yu2me++p+qm3I81j+bPnwvz/I8F3P+KMjHENXFVTrq6XFOqGNXl9O1tFMAiC7iqa6z/rra/66RYeLKrGLRXsbRGqL83+CGZsuHgWYkGb6QX6qbU83SXxg0CVFHtKISpv4GCWYMF4sOuJMBxhiujVzL0e2DtKOosf4CropQPAWBYQvgBhYKoBM0PRyvpAIxm4sBOMgolGKwRBo8G2zYZymzjwdMSiQRJf5R1eGxAd7XMajysHSYcshbLX7mxKBRf9G2rX0PYcFdP9inJT3dDOjPcVdz5SHOxDtLkyc+isPMYpAZ8eVMo/c5H0La4lQSYsHgeuUF0ubmYGUPbjEDdTfStunPUWbHwoOS2XMKSGyGIFHFNoBhYMonp2bIYiqRf6xCZ+EA/VW+WC8pfxbY7QGbbg7Wbj12IlkvIls5HdjSgfxgiUCI0ZqhoaJmwshtcJCc7a2mM/J4uKH3QvSleLUJVRTsuVh+OpMKFm/YBszGXvu2bSjKUHItNkYpNhiMtFS5DorX/fxJgkShaKxPGT0CFuo80xfL/6BEaRLHwfuPTMuU0Fgmrj7BuANybpVsPojYK+CutGqgV6ApQUvLBLu0k1M9TiYd2SCLcKAKxXJmQNVPTc7H8A6I7Roj0MIfHBViJv5sjiju5qgXFyHthSwc1//tth2TXyXAZ3qsdfl9Q7s4zw3pJd2Q4R4NrwwLZwX7IxIZFo7X/nxJgigLZsiRw0br6K0aF/n9K9t5ftcEUCy+VayDHwS1JB1jqA+USGZ2AUKqNLFyzBUzPchnatZUCKrOAxfRCSDgIy4jUtJUi+rPx0DG4PHmKC7MTFs/K3YflcbhDD/dsi1SlCqnv56WSzhh+buLvUxnWrek5tO00CWgSMIwEDKpYeEmcZWLh7qSDJeuK3BNXLCDPSE14bKJagHsHrIwY1G55YlAXcXO0k5OI9gzv1UGR7tKyclX+TmZWjlrNsCnwmQbeHsj3yJZMpPV/imjSJRxnTN92WIrBXsKxzGvXFiHSEDR9S7hP6ajN6wcQ2E5lDBtGQNpRNAloEqi9BAyuWPSXUIBoTCLXSwa5yQXgqQ3yEpgrsRUU/KSkVOnfoTnSAa5KGKyah/p0lhWbd8uhiBgZ0beTXInDEpDHzsrE+/tIN+Aq6alpYotwWPjVFFWntlV9XwlFxKkQmAwB43yQfoJAhPNC/Qomr2lNk4AmgT9XAvdMsehvKx+WBhdpTwQGkw9FEAgMphzJVObAQHJRJjIbyYHuCDOHxafJvmNn5JEB3WQb+CrhiO4MaNMYKym2UYu+E9ClosJahmAOlqsFp1giwMneTnxQL4a5G1rTJKBJ4K8hgXuuWPS3qYA4lC5IQlp/alaeWs/Hx9UJKyuy5qopOCfWitdhjdUJr7EAD1MFEHbOgUtlg+/Ih+NC9BkA80IAyrL+rhW4M/eK66G/bu23JgFNArWXwB+mWG68tBSEpZmtnITUAEsUczZn9AahZl1OCAo+MuyMVohi2I6IBtli7R43JwcVttZ9o/3UJKBJ4K8qgT9NsdwsEJZnRAW068V9TJAox8XEtRDjzZLSPtEk8FeXwF9IsfzVRaVdnyYBTQI1lQATb7SmSUCTgCYBg0pAUywGFad2ME0CmgQoAU2xaP1Ak4AmAYNLQFMsBhepdkBNApoENMWi9QFNApoEDC4BTbEYXKTaATUJaBLQFIvWBzQJaBIwuAQ0xWJwkWoH1CSgSUBTLFof0CSgScDgEtAUi8FFqh1Qk4AmAU2xaH1Ak4AmAYNLQFMsBhepdkBNApoENMWi9QFNApoEDC6B/wMG0AaGQSqYUwAAAABJRU5ErkJggg==\" alt=\"\">\n" +
                "</div>\n" +
                "<div>\n" +
                "    <table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%;\">\n" +
                "        <tr>\n" +
                "            <td>病人姓名</td>\n" +
                "            <td>${patientName!\"王维虎\"}</td>\n" +
                "            <td>性别</td>\n" +
                "            <td>${patientGender!\"男\"}</td>\n" +
                "            <td>年龄</td>\n" +
                "            <td>${patientAge!\"43\"}</td>\n" +
                "            <td>婚否</td>\n" +
                "            <td>${marriage!\"否\"}</td>\n" +
                "            <td>民族</td>\n" +
                "            <td>${nationality!\"汉族\"}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>申请科室</td>\n" +
                "            <td>${shenqingkeshi!\"儿科\"}</td>\n" +
                "            <td colspan=\"8\">申请人:${shenqingren!\"张申请\"}联系电话：${patientPhone!\"13656567887\"}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>会诊方式</td>\n" +
                "            <td colspan=\"9\">${doctorType!\"知名专家会诊\"}</td>\n" +
                "        </tr>\n" +
                "\t\t<tr>\n" +
                "            <td>拟请会诊医院</td>\n" +
                "            <td colspan=\"4\">${nishenqingyiyuan!\"某某市人民医院\"}</td>\n" +
                "            <td>拟请会诊科室</td>\n" +
                "            <td colspan=\"4\">${nishenqingkehsi!\"肾脏科\"}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>申请时间</td>\n" +
                "            <td colspan=\"4\">${shenqingshijian!\"2020-03-34 10:37:33\"}</td>\n" +
                "\t\t\t<td>期望会诊日期</td>\n" +
                "            <td colspan=\"4\">${qiwangriqi!\"2020-04-05\"}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>患者主诉</td>\n" +
                "            <td colspan=\"9\">\n" +
                "                ${huanzhezhusu!\"黄疸等特殊不适，休息后症状稍缓解，后就诊我科，考虑腹腔肿瘤，家属拒绝手术后出院。近日腹痛发作频繁，现再次就诊我院，急诊行腹部CT提示:右侧腹部占位性病变，与前片对比:明显进展。直\"}\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>既往史</td>\n" +
                "            <td colspan=\"9\">\n" +
                "                ${jiwangshi!\"既往史：既往10+年前在我院行右侧腹股沟疝修补术，8+年前在我院行膀胱肿物切除术；4+年前于我院行左侧腹股沟疝修补术；2年前因重度骨质疏松及脑萎缩就诊“西南医科大学附属医院,经治疗后好转出院，但平时仍有四肢不协调。\"}\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>过敏史</td>\n" +
                "            <td colspan=\"9\">${guominshi!\"无6、家族史：无\"}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>体格检查</td>\n" +
                "            <td colspan=\"9\">\n" +
                "                ${jiancha!\"查体：右侧腹部较左侧明显隆起，表面无红肿，未见胃肠型及蠕动波，无腹壁静脉曲张，下腹及双侧腹股沟见陈旧性手术疤痕，右中腹可扪及一巨大包块，质硬，不活动，边界不清，按压感疼痛，无反跳痛及肌紧张，肝脾肋下未触及，Murphy征阴性，肝肾区无叩痛，移动性浊音弱阳性，肠鸣音约2次/分\"}\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>辅助检查</td>\n" +
                "            <td colspan=\"9\">\n" +
                "                ${fuzhu!\"胸部及全腹部增强CT：1.右下腹占位，考虑来源于间叶组织肿瘤伴恶变可能性大，右侧腹膜及肠系膜转移待排；2.腹膜后稍大淋巴结；3.直肠肠壁稍增厚；4.双肾囊肿；5.右肺多发磨玻璃结节，转移？炎症？；6.双肺下叶慢性炎症；7.主动脉弓旁稍大淋巴结；8.左侧第10肋骨走形不规则，陈旧性骨折？请结合临床病史\"}\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>初步诊断</td>\n" +
                "            <td colspan=\"9\">${chubuzhenduan!\"诊断：1.腹腔肿瘤伴全身多处转移？；2.脑萎缩后遗症；3.骨质疏松；4.肺炎；5.双肾囊肿；6.陈旧性肋骨骨折。\"}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>目前治疗经过</td>\n" +
                "            <td colspan=\"9\">${zhiliaojingguo!\"对症治疗\"}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>会诊目的</td>\n" +
                "            <td colspan=\"9\">${mudi!\"寻求诊断\"}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>寻求治疗方案</td>\n" +
                "            <td colspan=\"9\">${fangan!\"无\"}</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    <div style=\"padding: 10px;box-sizing: border-box;\">\n" +
                "        特别说明：根据卫生部有关规定，远程咨询的意见只能作为一种参考，仅供病人的主管医生在明确诊断和进行有效治疗时参考采用，不做为法律依据\n" +
                "        。\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
    }


    @PostMapping("addTemplate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "html", value = "模板内容", required = true),
            @ApiImplicitParam(name = "name", value = "模板名", required = true),
            @ApiImplicitParam(name = "desc", value = "说明（备注）", required = false),
            @ApiImplicitParam(name = "testData", value = "模板测试数据", required = false)
    })
    @ApiOperation(value = "添加模板", notes = "添加模板，模板为完整的HTML")
    public HSResult addTemplate(String html, String name, String desc,String testData) {
        if (StrUtil.isBlank(html) || StrUtil.isBlank(name)){
            return HSResult.sayFail("添加失败，模板的内容和名字不能为空");
        }
        return templateService.addTemplate(html, name, desc,testData);
    }

    @PostMapping("editTemplate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模板内容id", required = true),
            @ApiImplicitParam(name = "html", value = "模板内容", required = true),
            @ApiImplicitParam(name = "name", value = "模板名", required = true),
            @ApiImplicitParam(name = "desc", value = "说明（备注）", required = false),
            @ApiImplicitParam(name = "testData", value = "模板测试数据", required = false)
    })
    @ApiOperation(value = "修改模板", notes = "修改模板，模板为完整的HTML")
    public HSResult editTemplate(String id, String html, String name, String desc,String testData) {
        if (StrUtil.isBlank(id) || StrUtil.isBlank(html) || StrUtil.isBlank(name)){
            return HSResult.sayFail("修改失败，模板的id，内容和名字均不能为空");
        }
        return templateService.editTemplate(id, html, name, desc,testData);
    }

    @PostMapping("deleteTemplate")
    @ApiImplicitParam(name = "id", value = "模板id", required = true)
    @ApiOperation(value = "删除模板", notes = "删除模板，必须传入模板ID，删除后不可恢复")
    public HSResult deleteTemplate(String id) {
        return templateService.deleteTemplate(id);
    }


    @PostMapping("queryAllTemplate")
    @ApiOperation(value = "查询当前所有的模板", notes = "查询当前所有模板")
    public HSResult queryAllTemplate() {
        return templateService.queryAllTemplate();
    }

    @PostMapping("getDemplateById")
    @ApiImplicitParam(name = "id", value = "模板id", required = true)
    @ApiOperation(value = "查询当前的模板", notes = "查询当前模板")
    public HSResult getDemplateById(String id) {
        return templateService.getDemplateById(id);
    }

}
