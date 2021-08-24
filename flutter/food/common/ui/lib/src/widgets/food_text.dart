import 'package:flutter/material.dart';
import '../core/food_text_styles.dart';

class FoodText extends Text {
  const FoodText._(this.data, this.style) : super(data, style: style);

  final String data;
  final TextStyle style;

  const FoodText.h1(String data) : this._(data, FoodTextStyles.h1);
  const FoodText.h2(String data) : this._(data, FoodTextStyles.h2);
  const FoodText.h3(String data) : this._(data, FoodTextStyles.h3);
  const FoodText.h4(String data) : this._(data, FoodTextStyles.h4);
  const FoodText.h5(String data) : this._(data, FoodTextStyles.h5);
  const FoodText.h6(String data) : this._(data, FoodTextStyles.h6);
  const FoodText.subtitle1(String data) : this._(data, FoodTextStyles.subtitle1);
  const FoodText.subtitle2(String data) : this._(data, FoodTextStyles.subtitle2);
  const FoodText.body1(String data) : this._(data, FoodTextStyles.body1);
  const FoodText.body2(String data) : this._(data, FoodTextStyles.body2);
  const FoodText.button(String data) : this._(data, FoodTextStyles.button);
  const FoodText.caption(String data) : this._(data, FoodTextStyles.caption);

  FoodText withColor(Color color) => FoodText._(data, style.copyWith(color: color));

  FoodText bold() => FoodText._(data, style.copyWith(fontWeight: FoodTextStyles.medium));
}