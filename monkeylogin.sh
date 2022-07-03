#! /bin/bash
echo ""
echo "[Waiting for launcher to start]"
LAUNCHER_READY=
while [[ -z ${LAUNCHER_READY} ]]; do
    UI_FOCUS=`adb shell "dumpsys window | grep mCurrentFocus"`
    echo "(DEBUG) Current focus: ${UI_FOCUS}"

    case $UI_FOCUS in
    *"Launcher"*)
        LAUNCHER_READY=true
    ;;
    "")
        echo "Waiting for window service..."
        sleep 3
    ;;
    *"Not Responding"*)
        echo "Detected an ANR! Dismissing..."
        adb shell input keyevent KEYCODE_DPAD_DOWN
        adb shell input keyevent KEYCODE_DPAD_DOWN
        adb shell input keyevent KEYCODE_ENTER
    ;;
    *)
        echo "Waiting for launcher..."
        sleep 3
    ;;
    esac
done
# install debug version on device
./gradlew installDebug
# package name
package="jacs.apps.monkeytest"

#start application, make sure to change package name and launcher activity name
adb shell am start -n ${package}/.MainActivity

echo "Launcher is ready :-)"
# length of commands arrays
length=6

# Given string of compose=(c) or xml=(x) how many login steps there are
viewtype="x,x,x,c,c,c"

# The id of the xml view or the testTag of the compose view
ids="username,password,login,cusername,cpassword,clogin"

# The action to be performed ex: tap or text
actions="text,text,tap,text,text,tap"

inputs="copypasteearth@gmail.com,test123,null,copypasteearth@gmail.com,test123,null"


# Setting IFS (input field separator) value as ","
IFS=','

# Reading the split string into array
read -ra viewtypearr <<< "$viewtype"
read -ra idsarr <<< "$ids"
read -ra actionarr <<< "$actions"
read -ra inputsarr <<< "$inputs"

# Print each value of the array by using the loop
for (( j=0; j<${length}; j++ ));
do
  success=
  while [[ -z ${success} ]]; do
    status=`adb shell uiautomator dump /sdcard/view.xml`
    echo status
    case $status in
        *"hierchary dumped to"*)
            success=true
        ;;
        "")
            echo "waiting for layout"
            sleep 3
        ;;
        *"ERROR"*)
            echo "Null layout"
        ;;
        *)
            echo "Waiting for layout"
            sleep 3
        ;;
        esac
    done
  adb pull /sdcard/view.xml
  cat view.xml
  echo "id is ${idsarr[$j]}"
  echo "input is ${inputsarr[$j]}"
  echo "viewtype = ${viewtypearr[$j]}"
  if test ${viewtypearr[$j]} = "x"
  then
    coords=$(xpath -e "//node[@resource-id=\"${package}:id/${idsarr[$j]}\"]/@bounds" view.xml | awk '{gsub("bounds=", "");print}' | egrep -o '[0-9]*,[0-9]*')
  else
    coords=$(xpath -e "//node[@resource-id=\"${idsarr[$j]}\"]/@bounds" view.xml | awk '{gsub("bounds=", "");print}' | egrep -o '[0-9]*,[0-9]*')
  fi
  echo "coords ${coords}"
  IFS=$'\n'
  arr=(${coords// / })
  echo "arr ${arr[4]}"
  num1=${arr[0]}
  echo "num1 ${num1}"
  num2=${arr[1]}
  echo "num2 ${num2}"
  IFS=','
  num1xy=(${num1// / })
  echo "num1xy ${num1xy}"
  num2xy=(${num2// / })
  echo "num2xy ${num2xy}"
  x=$(expr ${num1xy[0]} + ${num2xy[0]})
  echo "x = ${x}"
  y=$(expr ${num1xy[1]} + ${num2xy[1]})
  echo "y = ${y}"
  y=$(expr $y / 2)
  echo "y = ${y}"
  x=$(expr $x / 2)
  echo "x = ${x}"
  if test ${actionarr[$j]} = "text"
  then
    adb shell input tap $x $y
    adb shell input text ${inputsarr[$j]}
  else
    adb shell input tap $x $y
  fi
  sleep 2

done
adb shell monkey -p ${package} --ignore-security-exceptions --ignore-native-crashes -v 15000

