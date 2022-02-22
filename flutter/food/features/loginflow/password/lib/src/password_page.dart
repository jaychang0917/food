import 'package:base/base.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import 'password_bloc.dart';
import 'password_state.dart';
import 'password_event.dart';

class PasswordPage extends StatefulWidget {
  const PasswordPage({Key? key}) : super(key: key);

  @override
  _PasswordPageState createState() => _PasswordPageState();
}

class _PasswordPageState extends State<PasswordPage> {
  late TextEditingController _passwordTextController;

  @override
  void initState() {
    super.initState();
    _passwordTextController = TextEditingController();
  }

  @override
  void dispose() {
    super.dispose();
    _passwordTextController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final loginFlowData = ModalRoute.of(context)!.settings.arguments;

    return BlocProvider(
      create: (context) {
        final sessionRepository = RepositoryProvider.of<SessionRepository>(context);
        return PasswordBloc(
          loginFlowData: loginFlowData,
          sessionRepository: sessionRepository,
        );
      },
      child: BlocConsumer<PasswordBloc, PasswordState>(
        listener: (context, state) {
           if (state.navigation is MainNavigation) {
             Navigator.pushNamed(context, AppRoutes.main);
           }
        },
        builder: (context, state) {
          return Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              _header(context),
              if (state.isLoadingVisible) _loading(),
              _title(context),
              _passwordInput(context, state),
              if (state.isErrorViewVisible) _error(context),
              const Spacer(),
              if (state.isNextButtonVisible) _nextButton(context, state)
            ],
          );
        },
      ),
    );
  }

  Widget _header(BuildContext context) => FoodHeader.back(
    onNavigationIconTapped: () => Navigator.pop(context)
  );

  Widget _loading() => FoodLoading();

  Widget _title(BuildContext context) => Padding(
    padding: const EdgeInsets.all(FoodSpacings.normal),
    child: FoodText.h6(context.strings.password_title)
  );

  Widget _passwordInput(BuildContext context, PasswordState state) => Padding(
    padding: const EdgeInsets.symmetric(horizontal: FoodSpacings.normal),
    child: FoodTextField(
      controller: _passwordTextController,
      onChanged: (text) => BlocProvider.of<PasswordBloc>(context).add(PasswordTextChanged(text)),
      placeholder: context.strings.password_hint,
      type: FoodTextFieldType.password,
    ),
  );

  Widget _error(BuildContext context) => Padding(
    padding: const EdgeInsets.symmetric(horizontal: FoodSpacings.normal, vertical: FoodSpacings.medium),
    child: FoodText.body2(context.strings.password_error_invalid_password).withColor(FoodColors.error)
  );

  Widget _nextButton(BuildContext context, PasswordState state) => FoodButton(
    enabled: state.isNextButtonEnabled,
    text: context.strings.password_next,
    onTapped: () {
      BlocProvider.of<PasswordBloc>(context).add(NextButtonClicked(_passwordTextController.text));
    }
  );
}
