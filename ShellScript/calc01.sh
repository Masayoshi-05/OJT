#数値１、演算子、数値２を入力して四則演算を行い、結果を表示する

#1つ目の整数の入力処理
echo "1つ目の整数を入力してください。" 
read "input1"

#インプットが整数か判定
if [[ "$input1" =~ ^[0-9]+$ ]]; then
	num1=$((10#$input1))
else
	echo "整数を入力してください。"
	exit
fi

#演算子の入力処理
echo "演算子(+,-,*,/)を入力してください。"
read "operator"
#"operator"が演算子か判定
if [ ! "$operator" = "+" ] && [ ! "$operator" = "-" ] && [ ! "$operator" = "*" ] && [ ! "$operator" = "/" ]; then
	echo "四則演算(+,-,*,/)のみ使えます。"
	exit
fi

#2つ目の整数の入力処理
echo "2つ目の整数を入力してください。"
read "input2"

#"num2"が整数か判定
if [[ "$input2" =~ ^[0-9]+$ ]]; then
	num2=$((10#$input2))
else
        echo "整数を入力してください。"
        exit
fi

#計算結果を表示
echo ""
echo "計算結果："

#割り算の場合
if [ "$operator" = "/" ]; then
	if [ "$num2" = "0" ]; then
		echo "0で割ることはできません"
	else
		result=$(("$num1" "$operator" "$num2"))
		echo "$num1" "$operator" "$num2 = $result"
		echo "余り：" "$(("$num1" - "$num2" * "$result"))"
	fi

#それ以外の場合
else
	result=$(("$num1" "$operator" "$num2"))
	echo "$(($num1))" "$operator" "$(($num2))" "=" "$result"
fi
exit
