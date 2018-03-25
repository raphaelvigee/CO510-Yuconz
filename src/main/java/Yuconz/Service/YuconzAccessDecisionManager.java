package Yuconz.Service;

import Yuconz.Entity.User;
import Yuconz.Manager.LogManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.LogType;
import com.sallyf.sallyf.AccessDecisionManager.AccessDecisionManager;
import com.sallyf.sallyf.AccessDecisionManager.DecisionStrategy;
import com.sallyf.sallyf.Container.Container;
import com.sallyf.sallyf.ExpressionLanguage.ExpressionLanguage;

import java.lang.reflect.InvocationTargetException;

public class YuconzAccessDecisionManager extends AccessDecisionManager
{
    private final LogManager logManager;

    private final YuconzAuthenticationManager authenticationManager;

    public YuconzAccessDecisionManager(Container container, ExpressionLanguage expressionLanguage, LogManager logManager, YuconzAuthenticationManager authenticationManager)
    {
        super(container, expressionLanguage);
        this.logManager = logManager;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public <O> boolean vote(String attribute, O subject, DecisionStrategy strategy)
    {
        boolean decision = super.vote(attribute, subject, strategy);

        String details = "`" + attribute + "`";

        if (subject != null) {
            Class<?> klass = subject.getClass();

            details += " on `" + klass.getSimpleName() + "`";

            try {
                details += " ID: `" + klass.getMethod("getId").invoke(subject) + "`";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        logManager.log((User) authenticationManager.getUser(), LogType.AUTHORISATION_CHECK, details);

        return decision;
    }
}
