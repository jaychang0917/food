import 'package:flutter/material.dart';
import '../core/food_colors.dart';
import '../widgets/food_text.dart';

class FoodButton extends StatelessWidget {
  FoodButton({
    required this.text,
    this.color = FoodColors.accent,
    this.textColor = FoodColors.onAccent,
    this.enabled = true,
    this.onTapped
  });

  final String text;

  final Color color;

  final Color textColor;

  final bool enabled;

  final VoidCallback? onTapped;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 46,
      width: double.infinity,
      child: TextButton(
        style: ButtonStyle(
          shape: MaterialStateProperty.all(RoundedRectangleBorder(borderRadius: BorderRadius.zero)),
          backgroundColor: MaterialStateProperty.resolveWith(_color),
          fixedSize: MaterialStateProperty.all(Size.fromHeight(46))
        ),
        onPressed: enabled ? onTapped : null,
        child: FoodText.button(text).withColor(textColor)
      ),
    );
  }

  Color _color(Set<MaterialState> states) {
    if (states.contains(MaterialState.disabled)) {
      return color;
    }
    return color;
  }
}
