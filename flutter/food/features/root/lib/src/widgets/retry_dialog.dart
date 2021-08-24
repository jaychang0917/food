import 'package:base/base.dart';
import 'package:flutter/material.dart';

class RetryDialog extends StatelessWidget {
  const RetryDialog({Key? key, this.onRetryButtonTapped}) : super(key: key);

  final VoidCallback? onRetryButtonTapped;

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: FoodText.body1(context.strings.root_error_title),
      content: FoodText.body2(context.strings.root_error_message),
      actions: [
        TextButton(
          child: FoodText.button(context.strings.root_error_action),
          onPressed: onRetryButtonTapped,
        ),
      ],
    );
  }

  void show(BuildContext context) {
    showDialog(context: context, builder: (context) => build(context));
  }
}
